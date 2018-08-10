package by.intervale.akella266.todolist.views.today;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;

public class TodayPresenter implements TodayContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private TodayContract.View mTodayView;

    public TodayPresenter(TodayContract.View view) {
        mTasksRepo = Initializer.getTasksLocal();
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
        List<InboxItem> items = new ArrayList<>();
        items.add(new InboxItem("",
                mTasksRepo.query(new GetCurrentTasksSpecification())));
        items.add(new InboxItem("Completed",
                mTasksRepo.query(new GetCompletedTaskSpecification())));
        mTodayView.showTasks(items);
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


}
