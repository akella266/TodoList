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

    private List<TaskItem> mCurrentTaskItems;
    private List<TaskItem> mCompletedTaskItems;

    public LocalRepo() {
        mCurrentTaskItems = new ArrayList<>();
        mCompletedTaskItems = new ArrayList<>();
    }

    @Override
    public void add(TaskItem item) {
        mCurrentTaskItems.add(item);
    }

    public void addCompleted(TaskItem item) {
        mCompletedTaskItems.add(item);
    }


    @Override
    public void update(TaskItem item) {
        int pos = Collections.binarySearch(mCurrentTaskItems, item);
        mCurrentTaskItems.set(pos, item);
    }

    @Override
    public void remove(TaskItem item) {
        mCurrentTaskItems.remove(item);
    }

    @Override
    public List<TaskItem> query(Specification specification) {
        LocalSpecification spec = (LocalSpecification)specification;

        ResponseSpecification resp = spec.getType();
        switch (resp.getType()){
            case GET_CURRENT:{
                return mCurrentTaskItems;
            }
            case GET_COMPLETED:{
                return mCompletedTaskItems;
            }
            default:
                return null;
        }
    }
}
