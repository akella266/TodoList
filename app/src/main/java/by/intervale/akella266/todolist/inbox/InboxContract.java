package by.intervale.akella266.todolist.inbox;

import java.util.List;

import by.intervale.akella266.todolist.BasePresenter;
import by.intervale.akella266.todolist.BaseView;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;

public interface InboxContract {

    interface View extends BaseView<Presenter>{
        void showTasks(List<InboxItem> tasks);
    }
    interface Presenter extends BasePresenter {
        void loadTasks();
        void getTasks();
        void openTaskDetails(TaskItem task);
    }
}
