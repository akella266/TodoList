package by.intervale.akella266.todolist.rx;

import android.support.v7.widget.SearchView;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.GetTasksByNameSpecification;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.models.TaskItem;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {

    public static Observable<List<TaskItem>> fromView(SearchView searchView){
        final PublishSubject<List<TaskItem>> subject = PublishSubject.create();
        final Repository<TaskItem> repo = Initializer.getTasksRepo(searchView.getContext());
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
