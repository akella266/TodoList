package by.intervale.akella266.todolist.data.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.utils.TaskItem;

public class LocalRepo implements Repository<TaskItem> {

    private List<TaskItem> mTaskItems;

    public LocalRepo() {
        mTaskItems = new ArrayList<>();
    }

    @Override
    public void add(TaskItem item) {
        mTaskItems.add(item);
    }

    @Override
    public void update(TaskItem item) {
        int pos = Collections.binarySearch(mTaskItems, item);
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
            case GET_CURRENT:{
                List<TaskItem> currentTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems){
                    if (!item.isComplete())
                        currentTasks.add(item);
                }
                return currentTasks;
            }
            case GET_CURRENT_BY_NAME:{
                List<TaskItem> currentTasks = new ArrayList<>();
                String name = (String)resp.getArgs().get(0);
                for(TaskItem item : mTaskItems){
                    if (!item.isComplete() && item.getTitle().toLowerCase().contains(name.toLowerCase()))
                        currentTasks.add(item);
                }
                return currentTasks;
            }
            case GET_COMPLETED:{
                List<TaskItem> compeleteTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems){
                    if (item.isComplete())
                        compeleteTasks.add(item);
                }
                return compeleteTasks;
            }
            default:
                return null;
        }
    }
}
