package by.intervale.akella266.todolist.views.search;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface SearchRecyclerContract {
    interface View extends BaseView<Presenter> {
        void showTasks(List<TaskItem> items);
        void showTaskDetails(UUID itemId);
    }
    interface Presenter extends BasePresenter {
        void loadTasks(List<TaskItem> items);
        void openTaskDetails(TaskItem item);
        void completeTask(TaskItem item);
        void removeTask(TaskItem item);
    }
}
