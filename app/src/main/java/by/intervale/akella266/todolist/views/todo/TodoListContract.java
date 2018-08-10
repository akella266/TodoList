package by.intervale.akella266.todolist.views.todo;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface TodoListContract {

    interface View extends BaseView<Presenter>{
        void showGroups(List<Group> groups);
        void showGroupDetails(Group group);
        void showAddNewGroup();
        void showTasks(Group group);
        void showError(String message);
    }
    interface Presenter extends BasePresenter{
        void loadGroup();
        void openGroupDetails(Group group);
        void addNewGroup();
        void removeGroup(Group group);
        void openTasks(Group group);
    }
}
