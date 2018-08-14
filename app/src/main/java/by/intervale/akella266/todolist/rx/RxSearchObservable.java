package by.intervale.akella266.todolist.rx;

import android.support.v7.widget.SearchView;

import java.util.List;

import by.intervale.akella266.todolist.data.local.specifications.GetTasksByNameSpecification;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.models.TaskItem;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {

    public static Observable<List<TaskItem>> fromView(SearchView searchView){
        final PublishSubject<List<TaskItem>> subject = PublishSubject.create();
        final TaskItemLocalRepository repo = Initializer.getTasksLocal();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<TaskItem> items = repo.query(new GetTasksByNameSpecification(newText));
                subject.onNext(items);
                return true;
            }
        });

        return subject;
    }
}
