package by.intervale.akella266.todolist.views.today;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;

public class TodayPresenter implements TodayContract.Presenter {

    private Repository<TaskItem> mTasksRepo;
    private TodayContract.View mTodayView;

    public TodayPresenter(TodayContract.View view) {
        mTasksRepo = Initializer.getTasksRepo();
        mTodayView = view;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void openTaskDetails(TaskItem item) {
        mTodayView.showTaskDetails(item.getId());
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
        loadTasks();
    }

    @Override
    public List<TaskItem> getTasks() {
        List<TaskItem> items = new ArrayList<>();
        items.addAll(mTasksRepo.query(new GetCurrentTasksSpecification()));
        items.addAll(mTasksRepo.query(new GetCompletedTaskSpecification()));
        return items;
    }
}
