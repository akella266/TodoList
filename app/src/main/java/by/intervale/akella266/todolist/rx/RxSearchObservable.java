package by.intervale.akella266.todolist.rx;

import android.support.v7.widget.SearchView;
import android.util.Log;

import java.util.List;

import by.intervale.akella266.todolist.data.local.specifications.GetTasksByNameSpecification;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.utils.Initializer;
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
                Log.i("SearchView", "Submit:" + query);
//                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("SearchView", "Change:" + newText);
                List<TaskItem> items = repo.query(new GetTasksByNameSpecification(newText));
                subject.onNext(items);
                return true;
            }
        });

        return subject;
    }
}
