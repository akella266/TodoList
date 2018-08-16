package by.intervale.akella266.todolist.views.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.views.groupDetails.GroupDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.GroupAdapter;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemGroupClickListener;
import by.intervale.akella266.todolist.views.tasksGroup.TasksGroupActivity;

public class ToDoListFragment extends Fragment implements TodoListContract.View {

    private Unbinder unbinder;
    private TodoListContract.Presenter mPresenter;

    @BindView(R.id.linear_layout_default_group)
    LinearLayout mDefaultGroup;
    @BindView(R.id.textview_item_group_name)
    TextView mName;
    @BindView(R.id.textview_item_group_count_tasks)
    TextView mCount;
    @BindView(R.id.image_more)
    ImageView mImageMore;
    @BindView(R.id.recycler_todolist)
    RecyclerView mOtherGroups;
    private GroupAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new GroupAdapter(getContext(),
                mOnGroupItemClickListener,
                mOnPopupMenuItemClickListener);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
        unbinder = ButterKnife.bind(this, view);

        mOtherGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        mOtherGroups.setAdapter(mAdapter);

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

    @OnClick(R.id.fab_todolist)
    public void onFabClick(){
        mPresenter.addNewGroup();
    }

    @Override
    public void setPresenter(TodoListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showGroups(List<Group> groups) {
        initDefaultGroup(groups.get(0));
        mAdapter.setList(groups.subList(1, groups.size()));
    }

    @Override
    public void showGroupDetails(Group group) {
        Intent intent = GroupDetailsActivity.getStartIntent(getContext(), group.getIdUUID());
        startActivity(intent);
    }

    @Override
    public void showAddNewGroup() {
        Intent intent = GroupDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    @Override
    public void showTasks(Group group) {
        Intent intent = TasksGroupActivity.getStartIntent(getContext(), group);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mOtherGroups, message, Snackbar.LENGTH_LONG).show();
    }

    private void showPopupMenu(View view, final Group group){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.item_completed_or_group, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_edit:{
                        mOnPopupMenuItemClickListener.onEditClick(group);
                        return true;
                    }
                    case R.id.item_delete:{
                        mOnPopupMenuItemClickListener.onDeleteClick(group);
                        return true;
                    }
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    private void initDefaultGroup(final Group group){
        mName.setText(group.getName());
        mCount.setText(getString(R.string.item_group_count_tasks, group.getCountTasks()+""));
        mDefaultGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnGroupItemClickListener.onGroupItemClick(group);
            }
        });
        mImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, group);
            }
        });
    }

    OnGroupItemClickListener mOnGroupItemClickListener = new OnGroupItemClickListener() {
        @Override
        public void onGroupItemClick(Group group) {mPresenter.openTasks(group);}
    };

    OnPopupMenuItemGroupClickListener mOnPopupMenuItemClickListener = new OnPopupMenuItemGroupClickListener() {
        @Override
        public void onEditClick(Group group) { mPresenter.openGroupDetails(group);}

        @Override
        public void onDeleteClick(Group group) { mPresenter.removeGroup(group);}
    };
}
