package by.intervale.akella266.todolist.data.repositories.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.specifications.localJson.group.GetGroupByIdLocalSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;

public class TaskItemLocalRepository implements Repository<TaskItem> {

    private Context mContext;
    private List<TaskItem> mTaskItems;

    public TaskItemLocalRepository(Context context) {
        mTaskItems = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public void add(TaskItem item) {
        mTaskItems.add(item);
    }

    @Override
    public void update(TaskItem item) {
        for(int i = 0; i < mTaskItems.size(); i++){
            if (mTaskItems.get(i).getIdUUID().equals(item.getIdUUID())){
                mTaskItems.set(i, item);
                break;
            }
        }
    }

    @Override
    public void remove(TaskItem item) {
        mTaskItems.remove(item);
    }


    @Override
    public List<TaskItem> query(Specification spec) {
        spec.setDataSource(mTaskItems);
        return (List<TaskItem>) spec.getData();
    }
}
