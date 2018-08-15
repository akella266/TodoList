package by.intervale.akella266.todolist.views.tasksGroup;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.GetTasksByGroupIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TasksGroupPresenter implements TasksGroupContract.Presenter {

    private Context mContext;
    private Repository<TaskItem> mTasksRepo;
    private TasksGroupContract.View mView;
    private UUID groupId;

    public TasksGroupPresenter(Context context, TasksGroupContract.View mView, UUID groupId) {
        this.mContext = context;
        mTasksRepo = Initializer.getTasksRepo(mContext);
        this.mView = mView;
        this.groupId = groupId;
    }

    @Override
    public void loadTasks() {
        List<TaskItem> items = mTasksRepo.query(new GetTasksByGroupIdSpecification(groupId));
        if (items.size() == 0) mView.showNoTasks();
        else mView.showTasks(items);
    }

    @Override
    public void openTaskDetails(TaskItem item) {
        mView.showTasksDetails(item.getId());
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
        loadTasks();
    }

    @Override
    public void start() {
        loadTasks();
    }
}
