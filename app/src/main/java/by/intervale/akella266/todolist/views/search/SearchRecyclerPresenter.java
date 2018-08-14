package by.intervale.akella266.todolist.views.search;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.views.TypeData;
import by.intervale.akella266.todolist.views.inbox.OnItemChangedListener;

public class SearchRecyclerPresenter implements SearchRecyclerContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private SearchRecyclerContract.View mRecyclerView;
    private OnItemChangedListener mOnItemChangedListener;
    private TypeData mType;

    public SearchRecyclerPresenter(SearchRecyclerContract.View mRecyclerView, TypeData mType,
                                   OnItemChangedListener listener) {
        this.mRecyclerView = mRecyclerView;
        this.mType = mType;
        this.mOnItemChangedListener = listener;
        mTasksRepo = Initializer.getTasksLocal();
    }

    @Override
    public void start() {}

    @Override
    public void loadTasks(List<TaskItem> items) {
        List<TaskItem> filterItems = new ArrayList<>();
        for(int i = 0; i < items.size(); i++){
            switch (mType){
                case ACTIVE:{
                    if (!items.get(i).isComplete())
                        filterItems.add(items.get(i));
                    break;
                }
                case COMPLETED:{
                    if (items.get(i).isComplete())
                        filterItems.add(items.get(i));
                    break;
                }
            }
        }
        mRecyclerView.showTasks(filterItems);
    }

    @Override
    public void openTaskDetails(TaskItem item) {
        mRecyclerView.showTaskDetails(item.getId());
    }

    @Override
    public void completeTask(TaskItem item) {
        item.setComplete(true);
        mTasksRepo.update(item);
        mOnItemChangedListener.onItemChanged();
    }

    @Override
    public void removeTask(TaskItem item) {
        mTasksRepo.remove(item);
        mOnItemChangedListener.onItemChanged();
    }
}
