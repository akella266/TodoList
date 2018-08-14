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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;

import static android.content.Context.MODE_PRIVATE;

public class GroupJsonRepository implements Repository<Group> {

    private final String FILE_NAME = "groups";
    private Context mContext;

    public GroupJsonRepository(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void add(Group item) {

    }

    @Override
    public void update(Group item) {

    }

    @Override
    public void remove(Group item) {

    }

    @Override
    public List<Group> query(Specification specification) {
        return null;
    }
}
