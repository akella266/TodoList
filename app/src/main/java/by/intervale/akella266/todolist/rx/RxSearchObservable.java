package by.intervale.akella266.todolist.rx;

import android.support.v7.widget.SearchView;
import android.util.Log;

import java.util.List;

import by.intervale.akella266.todolist.data.local.GetCurrentTasksByNameSpecification;
import by.intervale.akella266.todolist.data.local.LocalRepo;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.TaskItem;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {

    public static Observable<List<TaskItem>> fromView(SearchView searchView){
        final PublishSubject<List<TaskItem>> subject = PublishSubject.create();
        final LocalRepo repo = Initializer.getTasksLocal();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<TaskItem> items = repo.query(new GetCurrentTasksByNameSpecification(newText));
                subject.onNext(items);
                return true;
            }
        });

        return subject;
    }
}
