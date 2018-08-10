package by.intervale.akella266.todolist.views.search;


import android.support.v7.widget.SearchView;

import java.util.List;

import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.views.BasePresenter;
import by.intervale.akella266.todolist.views.BaseView;

public interface SearchContract {

    interface View extends BaseView<Presenter>{
        void showNoResults();
        void showResults(List<TaskItem> items);
    }
    interface Presenter extends BasePresenter {
        void setUpSearchListener(SearchView searchView);
        void removeSearchListener();
        List<TaskItem> getRequestedTasks();
    }
}
