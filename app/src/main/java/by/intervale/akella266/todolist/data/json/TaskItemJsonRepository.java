package by.intervale.akella266.todolist.data.json;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.specifications.DecreaseCountTasksSpecification;
import by.intervale.akella266.todolist.data.specifications.GetGroupByIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.specifications.IncreaseCountTasksSpecification;

import static android.content.Context.MODE_PRIVATE;

public class TaskItemJsonRepository implements Repository<TaskItem> {

    private final String FILE_NAME = "tasks";
    private Gson mGson;
    private Context mContext;

    public TaskItemJsonRepository(Context mContext) {
        this.mContext = mContext;
        this.mGson = new GsonBuilder().create();
    }

    @Override
    public void add(TaskItem item) {
        List<TaskItem> itemsList = readItems();
        itemsList.add(item);
        Initializer.getGroupsRepo(mContext)
                .query(new IncreaseCountTasksSpecification(item.getGroupId()));
        writeItems(itemsList);
    }

    @Override
    public void update(TaskItem item) {
        List<TaskItem> items = readItems();
        for(int i = 0; i < items.size(); i++){
            if (items.get(i).getId().equals(item.getId())){
                if(!items.get(i).getGroupId().equals(item.getGroupId())) {
                    Initializer.getGroupsRepo(mContext)
                            .query(new DecreaseCountTasksSpecification(items.get(i).getGroupId()));
                    Initializer.getGroupsRepo(mContext)
                            .query(new IncreaseCountTasksSpecification(item.getGroupId()));
                }
                items.set(i, item);
                break;
            }
        }
        writeItems(items);
    }

    @Override
    public void remove(TaskItem item) {
        List<TaskItem> items = readItems();
        items.remove(item);
        Initializer.getGroupsRepo(mContext)
                .query(new DecreaseCountTasksSpecification(item.getGroupId()));
        writeItems(items);
    }

    @Override
    public List<TaskItem> query(Specification spec) {
        List<TaskItem> mTaskItems = readItems();
        ResponseSpecification resp = spec.getType();
        switch (resp.getType()) {
            case GET_CURRENT_TASKS: {
                List<TaskItem> currentTasks = new ArrayList<>();
                for (TaskItem item : mTaskItems)
                    if (!item.isComplete()) currentTasks.add(item);
                return currentTasks;
            }
            case GET_COMPLETED_TASKS: {
                List<TaskItem> compeleteTasks = new ArrayList<>();
                for (TaskItem item : mTaskItems)
                    if (item.isComplete()) compeleteTasks.add(item);
                return compeleteTasks;
            }
            case GET_BY_NAME_TASK:{
                List<TaskItem> tasks = new ArrayList<>();
                String name = (String)resp.getArgs().get(0);
                if(!name.isEmpty())
                    for (TaskItem item : mTaskItems)
                        if (item.getTitle().toLowerCase().contains(name.toLowerCase())) tasks.add(item);
                return tasks;
            }
            case GET_BY_ID_TASK:{
                List<TaskItem> tasks = new ArrayList<>();
                UUID itemId = (UUID)resp.getArgs().get(0);
                for(TaskItem item : mTaskItems){
                    if(item.getId().equals(itemId)){
                        tasks.add(item);
                        return tasks;
                    }
                }
            }
            case GET_BY_GROUP_ID_TASKS:{
                List<TaskItem> tasks = new ArrayList<>();
                UUID groupId = (UUID)resp.getArgs().get(0);
                for(TaskItem item : mTaskItems){
                    if(item.getGroupId().equals(groupId)){
                        tasks.add(item);
                    }
                }
                return tasks;
            }
            case REMOVE_BY_GROUP_ID:{
                UUID groupId = (UUID)resp.getArgs().get(0);
                for(int i = 0; i < mTaskItems.size(); i++){
                    if (mTaskItems.get(i).getGroupId().equals(groupId)){
                        mTaskItems.remove(i);
                        i--;
                    }
                }
                writeItems(mTaskItems);
                return null;
            }
            default:
                return null;
        }
    }

    private List<TaskItem> readItems(){
        TaskItem[] itemsArr = new TaskItem[0];
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mContext.openFileInput(FILE_NAME)));
            itemsArr = mGson.fromJson(reader, TaskItem[].class);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (itemsArr == null) return new ArrayList<>();
        else return new ArrayList<>(Arrays.asList(itemsArr));
    }

    private void writeItems(List<TaskItem> items){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    mContext.openFileOutput(FILE_NAME, MODE_PRIVATE)));
            String json = mGson.toJson(items.toArray(), Group[].class);
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
