package by.intervale.akella266.todolist.views.inbox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupByIdSpecification;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.views.TypeData;

public class InboxRecyclerPresenter implements InboxRecyclerContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private GroupLocalRepository mGroupRepo;
    private InboxRecyclerContract.View mRecyclerView;
    private OnItemChangedListener mOnItemChangedListener;
    private TypeData mType;

    public InboxRecyclerPresenter(InboxRecyclerContract.View mRecyclerView, TypeData mType,
                                  OnItemChangedListener listener) {
        mTasksRepo = Initializer.getTasksLocal();
        mGroupRepo = Initializer.getGroupsLocal();
        this.mOnItemChangedListener = listener;
        this.mRecyclerView = mRecyclerView;
        this.mType = mType;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void openTaskDetails(TaskItem item) {
        mRecyclerView.showTaskDetails(item.getId());
    }

    @Override
    public void loadTasks() {
        List<TaskItem> items = getTasks();
        mRecyclerView.showTasks(items);
    }

    @Override
    public void completeTask(TaskItem item) {
        item.setComplete(true);
        mTasksRepo.update(item);
        mOnItemChangedListener.onItemChanged();
    }

    @Override
    public void removeTask(TaskItem item) {
        mTasksRepo.remove(item);
        mOnItemChangedListener.onItemChanged();
    }

    @Override
    public String getNameCategory(TaskItem taskItem){
        switch (mType){
            case DATE:{
                return dateToString(taskItem.getDate());
            }
            case GROUP:{
                return mGroupRepo.query(
                        new GetGroupByIdSpecification(taskItem.getGroupId())).get(0).getName();
            }
            default:
                return "";
        }
    }


    @Override
    public List<TaskItem> getTasks() {
        return mTasksRepo.query(new GetCurrentTasksSpecification());
    }

    public String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(date);
    }
}
