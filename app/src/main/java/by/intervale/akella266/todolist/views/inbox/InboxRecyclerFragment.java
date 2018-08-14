package by.intervale.akella266.todolist.views.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.utils.SectionItemDecoration;
import by.intervale.akella266.todolist.views.taskDetails.TaskDetailsActivity;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemTaskClickListener;

public class InboxRecyclerFragment extends Fragment
        implements InboxRecyclerContract.View, Observer{

    private Unbinder unbinder;
    private InboxRecyclerContract.Presenter mPresenter;

    @BindView(R.id.fragment_recycler)
    RecyclerView mRecycler;
    private TasksAdapter mAdapter;
    public InboxRecyclerFragment() {}

    public static InboxRecyclerFragment newInstance(){
        return new InboxRecyclerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TasksAdapter(getContext(), mOnPopupMenuItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void showTasks(List<TaskItem> items, boolean withHeaders) {
        mAdapter.setTasks(items);
        if (mRecycler.getItemDecorationCount() == 0 && withHeaders)
            mRecycler.addItemDecoration(new SectionItemDecoration(
                    getResources().getDimensionPixelSize(R.dimen.header_height),
                    getResources().getColor(R.color.colorBackground),
                    sectionCallback));
    }

    @Override
    public void showTaskDetails(UUID itemId) {
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), itemId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(InboxRecyclerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void update(Observable observable, Object o) {
        mPresenter.loadTasks();
    }

    SectionItemDecoration.SectionCallback sectionCallback = new SectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0 ||
                        !mPresenter.getNameCategory(mPresenter.getTasks().get(position)).equals(
                                mPresenter.getNameCategory(mPresenter.getTasks().get(position - 1)));
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                return mPresenter.getNameCategory(mPresenter.getTasks().get(position));
            }
        };


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
