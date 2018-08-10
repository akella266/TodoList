package by.intervale.akella266.todolist.views.search;

import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.rx.RxSearchObservable;
import by.intervale.akella266.todolist.utils.Initializer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private SearchContract.View mSearchView;
    private Disposable mDisposable;
    private List<TaskItem> mListTasks;

    public SearchPresenter(SearchContract.View mSearchView) {
        this.mSearchView = mSearchView;
        mTasksRepo = Initializer.getTasksLocal();
        mListTasks = new ArrayList<>();
    }

    @Override
    public void setUpSearchListener(SearchView searchView) {
        mDisposable = RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskItem>>() {
                    @Override
                    public void accept(List<TaskItem> taskItems){
                        if (taskItems.isEmpty()){
                            mSearchView.showNoResults();
                        }
                        else{
                            mListTasks.clear();
                            mListTasks.addAll(taskItems);
                            mSearchView.showResults(taskItems);
                        }

                    }
                });
    }

    @Override
    public void removeSearchListener() {
        if (mDisposable != null)
            mDisposable.dispose();
        mListTasks.clear();
        mSearchView.showNoResults();
    }

    @Override
    public List<TaskItem> getRequestedTasks() {
        return mListTasks;
    }

    @Override
    public void start() {
        if (mListTasks.isEmpty())
            mSearchView.showNoResults();
        else
            mSearchView.showResults(mListTasks);
    }
}
