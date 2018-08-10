package by.intervale.akella266.todolist.views.inbox;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.views.TypeData;

public interface InboxRecyclerContract {

    interface View extends BaseView<Presenter>{
        void showTasks(List<InboxItem> items);
        void showTaskDetails(UUID itemId);
    }
    interface Presenter extends BasePresenter{
        void loadTasks();
        void openTaskDetails(TaskItem item);
        void completeTask(TaskItem item);
        void removeTask(TaskItem item);
    }
}
