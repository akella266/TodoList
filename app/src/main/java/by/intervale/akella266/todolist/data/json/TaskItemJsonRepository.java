package by.intervale.akella266.todolist.data.json;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.interfaces.json.JsonSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;

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
        writeItems(itemsList);
    }

    @Override
    public void update(TaskItem item) {

    }

    @Override
    public void remove(TaskItem item) {

    }

    @Override
    public List<TaskItem> query(Specification specification) {
        JsonSpecification spec = (JsonSpecification)specification;
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
            default:
                return null;
        }
    }

    private List<TaskItem> readItems(){
        TaskItem[] itemsArr = new TaskItem[0];
        try {
            itemsArr = mGson.fromJson(
                    new InputStreamReader(mContext.openFileInput(FILE_NAME), String.valueOf(MODE_PRIVATE)),
                    TaskItem[].class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(itemsArr));
    }

    private void writeItems(List<TaskItem> items){
        try {
            mGson.toJson(items.toArray(), TaskItem[].class, new JsonWriter(new OutputStreamWriter(
                    mContext.openFileOutput(FILE_NAME, MODE_PRIVATE))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
