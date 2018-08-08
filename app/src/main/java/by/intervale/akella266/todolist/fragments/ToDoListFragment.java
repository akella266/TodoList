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

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.GroupDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.GroupAdapter;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupsSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.ItemTouchCallback;

public class ToDoListFragment extends Fragment {

    private Unbinder unbinder;
    private Repository<Group> mRepo;

    @BindView(R.id.linear_layout_default_group)
    LinearLayout mDefaultGroup;
    @BindView(R.id.recycler_todolist)
    RecyclerView mOtherGroups;

    private GroupAdapter mAdapter;
    private List<Group> mGroupsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
        unbinder = ButterKnife.bind(this, view);

        mOtherGroups.setLayoutManager(new LinearLayoutManager(getContext()));

        mRepo = Initializer.getGroupsLocal();
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_todolist)
    public void onFabClick(){
        Intent intent = GroupDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    private void updateUI(){
        if(mAdapter == null){
            mGroupsList = mRepo.query(new GetGroupsSpecification());
            mAdapter = new GroupAdapter(getContext());
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchCallback(mAdapter));
            helper.attachToRecyclerView(mOtherGroups);
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
}
