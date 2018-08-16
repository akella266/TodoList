package by.intervale.akella266.todolist.data.repositories.json;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.specifications.localJson.group.DecreaseCountTasksLocalSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.specifications.localJson.group.IncreaseCountTasksLocalSpecification;
import by.intervale.akella266.todolist.data.specifications.localJson.task.RemoveTaskByGroupIdLocalSpecification;

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
                .query(new IncreaseCountTasksLocalSpecification(item.getGroupIdUUID()));
        writeItems(itemsList);
    }

    @Override
    public void update(TaskItem item) {
        List<TaskItem> items = readItems();
        for(int i = 0; i < items.size(); i++){
            if (items.get(i).getIdUUID().equals(item.getIdUUID())){
                if(!items.get(i).getGroupIdUUID().equals(item.getGroupIdUUID())) {
                    Initializer.getGroupsRepo(mContext)
                            .query(new DecreaseCountTasksLocalSpecification(items.get(i).getGroupIdUUID()));
                    Initializer.getGroupsRepo(mContext)
                            .query(new IncreaseCountTasksLocalSpecification(item.getGroupIdUUID()));
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
                .query(new DecreaseCountTasksLocalSpecification(item.getGroupIdUUID()));
        writeItems(items);
    }

    @Override
    public List<TaskItem> query(Specification spec) {
        List<TaskItem> mTaskItems = readItems();
        spec.setDataSource(mTaskItems);
        List<TaskItem> result = spec.getData();
        if(spec instanceof RemoveTaskByGroupIdLocalSpecification)
            writeItems(result);
        return result;
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
