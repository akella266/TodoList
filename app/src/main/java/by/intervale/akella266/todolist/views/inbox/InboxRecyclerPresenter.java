package by.intervale.akella266.todolist.views.inbox;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.localJson.task.GetCurrentTasksLocalSpecification;
import by.intervale.akella266.todolist.data.specifications.localJson.group.GetGroupByIdLocalSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.views.TypeData;

public class InboxRecyclerPresenter implements InboxRecyclerContract.Presenter {

    private Context mContext;
    private Repository<TaskItem> mTasksRepo;
    private Repository<Group> mGroupRepo;
    private InboxRecyclerContract.View mRecyclerView;
    private OnItemChangedListener mOnItemChangedListener;
    private TypeData mType;
    private boolean mWithHeaders;

    public InboxRecyclerPresenter(Context context, InboxRecyclerContract.View mRecyclerView, TypeData mType,
                                  boolean withHeaders, OnItemChangedListener listener) {
        this.mContext = context;
        mTasksRepo = Initializer.getTasksRepo(mContext);
        mGroupRepo = Initializer.getGroupsRepo(mContext);
        this.mOnItemChangedListener = listener;
        this.mRecyclerView = mRecyclerView;
        this.mType = mType;
        this.mWithHeaders = withHeaders;
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
        mRecyclerView.showTasks(items, mWithHeaders);
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
                        new GetGroupByIdLocalSpecification(taskItem.getGroupId())).get(0).getName();
            }
            default:
                return "";
        }
    }


    @Override
    public List<TaskItem> getTasks() {
        return mTasksRepo.query(new GetCurrentTasksLocalSpecification());
    }

    public String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(date);
    }
}
