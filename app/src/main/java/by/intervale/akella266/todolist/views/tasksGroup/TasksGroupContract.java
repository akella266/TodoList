package by.intervale.akella266.todolist.views.tasksGroup;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface TasksGroupContract {

    interface View extends BaseView<Presenter>{
        void showTasks(List<TaskItem> items);
        void showTasksDetails(UUID itemId);
        void showNoTasks();
    }
    interface Presenter extends BasePresenter{
        void loadTasks();
        void openTaskDetails(TaskItem item);
        void completeTask(TaskItem item);
        void removeTask(TaskItem item);
    }
}
