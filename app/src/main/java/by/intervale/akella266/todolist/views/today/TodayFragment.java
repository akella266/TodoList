package by.intervale.akella266.todolist.views.today;

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

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.views.taskDetails.TaskDetailsActivity;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemTaskClickListener;

public class TodayFragment extends Fragment implements TodayContract.View{

    private Unbinder unbinder;
    private TodayContract.Presenter mPresenter;

    @BindView(R.id.recycler_today)
    RecyclerView mRecycler;
    private CommonAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CommonAdapter(getContext(), mOnPopupMenuItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
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
    public void showTasks(List<InboxItem> items) {
        mAdapter.setList(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTaskDetails(UUID itemId) {
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), itemId);
        startActivity(intent);
    }

    @Override
    public void showNewTask() {
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    @Override
    public void setPresenter(TodayContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @OnClick(R.id.fab_today)
    public void onFabClick(){
        mPresenter.addNewTask();
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
