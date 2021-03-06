package by.intervale.akella266.todolist.views.today;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;

public interface TodayContract {

    interface View extends BaseView<Presenter>{
        void showTasks(List<TaskItem> items);
        void showTaskDetails(UUID itemId);
        void showNewTask();
    }
    interface Presenter extends BasePresenter{
        void addNewTask();
        void loadTasks();
        void openTaskDetails(TaskItem item);
        void completeTask(TaskItem item);
        void removeTask(TaskItem item);
        List<TaskItem> getTasks();
    }
}
