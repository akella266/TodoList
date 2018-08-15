package by.intervale.akella266.todolist.data.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.specifications.GetGroupByIdSpecification;
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
                .query(new GetGroupByIdSpecification(item.getGroupId()))
                .get(0).increaseCountTasks();
    }

    @Override
    public void update(TaskItem item) {
        for(int i = 0; i < mTaskItems.size(); i++){
            if (mTaskItems.get(i).getId().equals(item.getId())){
                if(!mTaskItems.get(i).getGroupId().equals(item.getGroupId())) {
                    Initializer.getGroupsRepo(mContext)
                            .query(new GetGroupByIdSpecification(mTaskItems.get(i).getGroupId()))
                            .get(0).decreaseCountTasks();
                    Initializer.getGroupsRepo(mContext)
                            .query(new GetGroupByIdSpecification(item.getGroupId()))
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
                .query(new GetGroupByIdSpecification(item.getGroupId()))
                .get(0).decreaseCountTasks();
    }


    @Override
    public List<TaskItem> query(Specification spec) {
        ResponseSpecification resp = spec.getType();
        switch (resp.getType()){
            case GET_CURRENT_TASKS:{
                List<TaskItem> currentTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems)
                    if (!item.isComplete()) currentTasks.add(item);
                return currentTasks;
            }
            case GET_COMPLETED_TASKS:{
                List<TaskItem> compeleteTasks = new ArrayList<>();
                for(TaskItem item : mTaskItems)
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
                return null;
            }
            default:
                return null;
        }
    }
}
