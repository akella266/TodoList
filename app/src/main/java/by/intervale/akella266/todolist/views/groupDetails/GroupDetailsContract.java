package by.intervale.akella266.todolist.views.groupDetails;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface GroupDetailsContract {

    interface View extends BaseView<Presenter>{
        void showGroup(Group group);
        void showError(int message);
    }
    interface Presenter extends BasePresenter {
        void loadGroup();
        void saveGroup(String name);
        void removeGroup();
    }
}
