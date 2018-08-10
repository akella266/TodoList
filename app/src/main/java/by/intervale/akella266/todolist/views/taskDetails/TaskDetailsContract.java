package by.intervale.akella266.todolist.views.taskDetails;

import android.content.Context;

import java.util.Date;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Priority;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface TaskDetailsContract {

    interface View extends BaseView<Presenter>{
        void showTask(TaskItem item);
        void showDateTimePicker(Date defaultDate);
        void showChoosingPriority();
        void showChoosingGroup();
        void showPriority(Priority priority);
        void showDate(Date date);
        void showGroup(String name);
        void showError(String message);
    }
    interface Presenter extends BasePresenter{
        void loadTask();
        void openDateTimePicker();
        void openChoosingPriority();
        void openChoosingGroup();
        void saveTask(String title, String notes);
        void removeTask();
        void setDate(Date date);
        void setPriority(Priority priority);
        void setGroup(Group group);
        void setReminder(Context context, boolean b);
    }
}
