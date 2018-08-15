package by.intervale.akella266.todolist.data.json;

import android.content.Context;
import android.util.JsonWriter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;

import static android.content.Context.MODE_PRIVATE;

public class GroupJsonRepository implements Repository<Group> {

    private Gson mGson;
    private final String FILE_NAME = "groups";
    private Context mContext;

    public GroupJsonRepository(Context mContext) {
        this.mGson = new GsonBuilder().create();
        this.mContext = mContext;
    }

    @Override
    public void add(Group item) {
        List<Group> groups = readItems();
        groups.add(item);
        writeItems(groups);
    }

    @Override
    public void update(Group item) {
        List<Group> mGroups = readItems();
        for(int i = 0; i < mGroups.size(); i++){
            if (mGroups.get(i).getId().equals(item.getId())){
                mGroups.set(i, item);
                break;
            }
        }
        writeItems(mGroups);
    }

    @Override
    public void remove(Group item) {
        List<Group> groups = readItems();
        groups.remove(item);
        writeItems(groups);
    }

    @Override
    public List<Group> query(Specification spec) {
        ResponseSpecification resp = spec.getType();
        List<Group> mGroups = readItems();
        switch (resp.getType()){
            case GET_GROUPS:{
                return mGroups;
            }
            case GET_GROUP_BY_ID:{
                UUID id = (UUID) resp.getArgs().get(0);
                Group resultGroup = new Group();
                for(Group group : mGroups)
                    if (group.getId().equals(id)) resultGroup = group;
                List<Group> resultList = new ArrayList<>();
                resultList.add(resultGroup);
                return resultList;
            }
            case INCREASE_COUNT_TASKS:{
                UUID id = (UUID) resp.getArgs().get(0);
                Group resultGroup = new Group();
                for(Group group : mGroups)
                    if (group.getId().equals(id)) resultGroup = group;
                resultGroup.increaseCountTasks();
                writeItems(mGroups);
                return null;
            }
            case DECREASE_COUNT_TASKS:{
                UUID id = (UUID) resp.getArgs().get(0);
                Group resultGroup = new Group();
                for(Group group : mGroups)
                    if (group.getId().equals(id)) resultGroup = group;
                resultGroup.decreaseCountTasks();
                writeItems(mGroups);
                return null;
            }
            default:
                return null;
        }
    }


    private List<Group> readItems(){
        Group[] itemsArr = new Group[0];
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mContext.openFileInput(FILE_NAME)));
            itemsArr = mGson.fromJson(reader, Group[].class);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (itemsArr == null) return new ArrayList<>();
        else return new ArrayList<>(Arrays.asList(itemsArr));
    }

    private void writeItems(List<Group> items){
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
