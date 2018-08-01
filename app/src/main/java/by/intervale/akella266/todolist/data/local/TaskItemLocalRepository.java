package by.intervale.akella266.todolist.data.local;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskItemLocalRepository implements Repository<TaskItem> {

    private List<TaskItem> mTaskItems;

    public TaskItemLocalRepository() {
        mTaskItems = new ArrayList<>();
    }

    @Override
    public void add(TaskItem item) {
        mTaskItems.add(item);
    }

    @Override
    public void update(TaskItem item) {
        int pos = -1;
        for(int i = 0; i < mTaskItems.size(); i++){
            if (mTaskItems.get(i).getId() == item.getId()){
                pos = i;
            }
        }
        mTaskItems.set(pos, item);
    }

    @Override
    public void remove(TaskItem item) {
        mTaskItems.remove(item);
    }

    @Override
    public List<TaskItem> query(Specification specification) {
        LocalSpecification spec = (LocalSpecification)specification;

        ResponseSpecification resp = spec.getType();
        switch (resp.getType()){
            case GET_CURRENT_TASKS:{
                List<TaskItem> currentTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems){
                    if (!item.isComplete())
                        currentTasks.add(item);
                }
                return currentTasks;
            }
            case GET_COMPLETED_TASKS:{
                List<TaskItem> compeleteTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems){
                    if (item.isComplete())
                        compeleteTasks.add(item);
                }
                return compeleteTasks;
            }
            case GET_BY_NAME_TASKS:{
                List<TaskItem> tasks = new ArrayList<>();
                String name = (String)resp.getArgs().get(0);
                if(!name.isEmpty()) {
                    for (TaskItem item : mTaskItems) {
                        if (item.getTitle().toLowerCase().contains(name.toLowerCase()))
                            tasks.add(item);
                    }
                }
                return tasks;
            }
            default:
                return null;
        }
    }
}
