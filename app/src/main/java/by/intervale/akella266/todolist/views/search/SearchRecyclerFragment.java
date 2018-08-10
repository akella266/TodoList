package by.intervale.akella266.todolist.views.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.views.taskDetails.TaskDetailsActivity;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemTaskClickListener;

public class SearchRecyclerFragment extends Fragment implements SearchRecyclerContract.View {

    private Unbinder unbinder;
    private SearchRecyclerContract.Presenter mPresenter;

    @BindView(R.id.fragment_recycler)
    RecyclerView mRecycler;
    private TasksAdapter mAdapter;

    public SearchRecyclerFragment() {}

    public static SearchRecyclerFragment newInstance(){
        return new SearchRecyclerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TasksAdapter(getContext(), new ArrayList<TaskItem>(), mOnPopupMenuItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(SearchRecyclerContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showTasks(List<TaskItem> items) {
        mAdapter.update(items);
    }

    @Override
    public void showTaskDetails(UUID itemId) {
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), itemId);
        startActivity(intent);
    }


    OnPopupMenuItemTaskClickListener mOnPopupMenuItemClickListener = new OnPopupMenuItemTaskClickListener() {
        @Override
        public void onEditClick(TaskItem item) {
            mPresenter.openTaskDetails(item);
        }

        @Override
        public void onCompleteClick(TaskItem item) {
            mPresenter.completeTask(item);
        }

        @Override
        public void onDeleteClick(TaskItem item) {
            mPresenter.removeTask(item);
        }
    };
}
