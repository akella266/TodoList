package by.intervale.akella266.todolist.views.tasksGroup;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetTasksByGroupIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TasksGroupPresenter implements TasksGroupContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private GroupLocalRepository mGroupRepo;
    private TasksGroupContract.View mView;
    private UUID groupId;

    public TasksGroupPresenter(TasksGroupContract.View mView, UUID groupId) {
        mTasksRepo = Initializer.getTasksLocal();
        mGroupRepo = Initializer.getGroupsLocal();
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
