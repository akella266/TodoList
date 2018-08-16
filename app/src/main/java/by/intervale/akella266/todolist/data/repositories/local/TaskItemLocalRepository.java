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
        Initializer.getGroupsRepo(mContext)
                .query(new GetGroupByIdLocalSpecification(item.getGroupIdUUID()))
                .get(0).increaseCountTasks();
    }

    @Override
    public void update(TaskItem item) {
        for(int i = 0; i < mTaskItems.size(); i++){
            if (mTaskItems.get(i).getIdUUID().equals(item.getIdUUID())){
                if(!mTaskItems.get(i).getGroupIdUUID().equals(item.getGroupIdUUID())) {
                    Initializer.getGroupsRepo(mContext)
                            .query(new GetGroupByIdLocalSpecification(mTaskItems.get(i).getGroupIdUUID()))
                            .get(0).decreaseCountTasks();
                    Initializer.getGroupsRepo(mContext)
                            .query(new GetGroupByIdLocalSpecification(item.getGroupIdUUID()))
                            .get(0).increaseCountTasks();
                }
                mTaskItems.set(i, item);
                break;
            }
        }
    }

    @Override
    public void remove(TaskItem item) {
        boolean tmp = mTaskItems.remove(item);
        Initializer.getGroupsRepo(mContext)
                .query(new GetGroupByIdLocalSpecification(item.getGroupIdUUID()))
                .get(0).decreaseCountTasks();
    }


    @Override
    public List<TaskItem> query(Specification spec) {
        spec.setDataSource(mTaskItems);
        return (List<TaskItem>) spec.getData();
    }
}
