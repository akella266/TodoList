package by.intervale.akella266.todolist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.intervale.akella266.todolist.GroupDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.GroupAdapter;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupsSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.ItemTouchActions;
import by.intervale.akella266.todolist.utils.ItemTouchCallback;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class ToDoListFragment extends Fragment
        implements OnToolbarButtonsClickListener, Observer{

    private Repository<Group> mRepo;
    private LinearLayout mDefaultGroup;
    private RecyclerView mOtherGroups;
    private GroupAdapter mAdapter;
    private List<Group> mGroupsList;
    private ItemTouchHelper helper;
    private boolean isEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);

        isEdit = false;
        mDefaultGroup = view.findViewById(R.id.linear_layout_default_group);
        mOtherGroups = view.findViewById(R.id.recycler_todolist);
        mOtherGroups.setLayoutManager(new LinearLayoutManager(getContext()));

        mRepo = Initializer.getGroupsLocal();
        initSwipeMenu();
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void update(Observable observable, Object o) {
        resetStateEditing();
        updateUI();
    }

    @Override
    public void onLeftButtonClick(View view) {
        changeStateEditing(view);
        updateUI();
    }

    @Override
    public void onRightButtonClick(View view) {
        resetStateEditing();
        Intent intent = GroupDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    private void initSwipeMenu() {
        helper = new ItemTouchHelper(new ItemTouchCallback(getContext(),1,
                new ItemTouchActions() {
                    @Override
                    public void onLeftClick(RecyclerView.Adapter adapter, int position) {}

                    @Override
                    public void onRightClick(RecyclerView.Adapter adapter, int position) {
                        List<TaskItem> items = new ArrayList<>();
                        items.addAll(Initializer.getTasksLocal()
                                            .query(new GetCurrentTasksSpecification()));
                        items.addAll(Initializer.getTasksLocal()
                                            .query(new GetCompletedTaskSpecification()));
                        Group deletingGroup = ((GroupAdapter)adapter).getList().get(position);
                        for(TaskItem item : items){
                            if (item.getGroupId().equals(deletingGroup.getId())){
                                Initializer.getTasksLocal().remove(item);
                            }
                        }
                        Initializer.getGroupsLocal().remove(deletingGroup);
                        updateUI();
                    }
                }));
    }

    private void updateUI(){
        if(mAdapter == null){
            mGroupsList = mRepo.query(new GetGroupsSpecification());
            mAdapter = new GroupAdapter(getContext());
        }
        if(mOtherGroups.getAdapter() == null) mOtherGroups.setAdapter(mAdapter);
        initDefaultGroup();
        mAdapter.setList(mGroupsList.subList(1, mGroupsList.size()));
        mAdapter.notifyDataSetChanged();
    }

    private void initDefaultGroup(){
        TextView name = mDefaultGroup.findViewById(R.id.textview_item_group_name);
        TextView count = mDefaultGroup.findViewById(R.id.textview_item_group_count_tasks);

        Group def = mGroupsList.get(0);
        name.setText(def.getName());
        count.setText(getString(R.string.item_group_count_tasks, def.getCountTasks()+""));
    }

    private void changeStateEditing(View view){
        if(isEdit){
            helper.attachToRecyclerView(null);
            ((TextView)view).setText(getString(R.string.toolbar_button_edit));
            isEdit = false;
        }
        else {
            helper.attachToRecyclerView(mOtherGroups);
            ((TextView)view).setText(getString(R.string.toolbar_button_done));
            isEdit = true;
        }
    }

    private void resetStateEditing(){
        if(isEdit){
            helper.attachToRecyclerView(null);
            isEdit = false;
            mOtherGroups.setAdapter(null);
        }
    }


}
