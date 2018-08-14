package by.intervale.akella266.todolist.views.tasksGroup;

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
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemTaskClickListener;
import by.intervale.akella266.todolist.views.taskDetails.TaskDetailsActivity;

public class TasksGroupFragment extends Fragment
        implements TasksGroupContract.View{

    private Unbinder unbinder;
    private TasksGroupContract.Presenter mPresenter;

    @BindView(R.id.textview_no_tasks)
    TextView mNoTasks;
    @BindView(R.id.recycler_tasks_group)
    RecyclerView mRecycler;
    private TasksAdapter mAdapter;

    public static TasksGroupFragment newInstance(){
        return new TasksGroupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TasksAdapter(getContext(), mOnPopupMenuItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_group, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTasks(List<TaskItem> items) {
        mNoTasks.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mAdapter.update(items);
    }

    @Override
    public void showTasksDetails(UUID itemId) {
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), itemId);
        startActivity(intent);
    }

    @Override
    public void showNoTasks() {
        mNoTasks.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(TasksGroupContract.Presenter presenter) {
        this.mPresenter = presenter;
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
