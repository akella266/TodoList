package by.intervale.akella266.todolist.views.today;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.db.group.DecreaseCountTasksDbSpecification;
import by.intervale.akella266.todolist.data.specifications.db.task.GetCompletedTaskDbSpecification;
import by.intervale.akella266.todolist.data.specifications.db.task.GetCurrentTasksDbSpecification;
import by.intervale.akella266.todolist.data.specifications.localJson.task.GetCompletedTaskLocalSpecification;
import by.intervale.akella266.todolist.data.specifications.localJson.task.GetCurrentTasksLocalSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;

public class TodayPresenter implements TodayContract.Presenter {

    private Context mContext;
    private Repository<TaskItem> mTasksRepo;
    private TodayContract.View mTodayView;

    public TodayPresenter(Context context, TodayContract.View view) {
        this.mContext = context;
        mTasksRepo = Initializer.getTasksRepo(mContext);
        mTodayView = view;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void openTaskDetails(TaskItem item) {
        mTodayView.showTaskDetails(item.getIdUUID());
    }

    @Override
    public void loadTasks() {
        mTodayView.showTasks(getTasks());
    }

    @Override
    public void addNewTask() {
        mTodayView.showNewTask();
    }

    @Override
    public void completeTask(TaskItem item) {
        item.setComplete(true);
        mTasksRepo.update(item);
        loadTasks();
    }

    @Override
    public void removeTask(TaskItem item) {
        mTasksRepo.remove(item);
        Initializer.getGroupsRepo(mContext).query(new DecreaseCountTasksDbSpecification(item.getGroupIdUUID()));
        loadTasks();
    }

    @Override
    public List<TaskItem> getTasks() {
        List<TaskItem> items = new ArrayList<>();
        items.addAll(mTasksRepo.query(new GetCurrentTasksDbSpecification()));
        items.addAll(mTasksRepo.query(new GetCompletedTaskDbSpecification()));
        return items;
    }
}
