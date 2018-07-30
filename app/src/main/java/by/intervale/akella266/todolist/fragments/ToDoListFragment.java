package by.intervale.akella266.todolist.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import by.intervale.akella266.todolist.GroupDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.GroupAdapter;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupsSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;

public class ToDoListFragment extends Fragment
        implements View.OnClickListener{

    private Repository<Group> mRepo;
    private LinearLayout mDefaultGroup;
    private RecyclerView mOtherGroups;
    private GroupAdapter mAdapter;
    private List<Group> mGroupsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
        setHasOptionsMenu(true);

        mDefaultGroup = view.findViewById(R.id.default_group);
        mOtherGroups = view.findViewById(R.id.todolist_recycler);
        mOtherGroups.setLayoutManager(new LinearLayoutManager(getContext()));

        mRepo = Initializer.getGroupsLocal();
        updateUI();
        return view;
    }

    private void updateUI(){
        if(mAdapter == null){
            mGroupsList = mRepo.query(new GetGroupsSpecification());
            mAdapter = new GroupAdapter(getContext());
        }
        initDefaultGroup();
        mAdapter.setList(mGroupsList.subList(1, mGroupsList.size()));
        mOtherGroups.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initDefaultGroup(){
        TextView name = mDefaultGroup.findViewById(R.id.item_group_name_group);
        TextView count = mDefaultGroup.findViewById(R.id.item_group_count_tasks);

        Group def = mGroupsList.get(0);
        name.setText(def.getName());
        count.setText(getString(R.string.item_group_count_tasks, def.getCountTasks()+""));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_todolist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fragment_todolist_add_group:{
                Intent intent = GroupDetailsActivity.newIntent(getContext(), null);
                startActivity(intent);
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
