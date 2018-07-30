package by.intervale.akella266.todolist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;

public class TodayFragment extends Fragment
        implements View.OnClickListener{

    private Repository<TaskItem> mRepo;
    private Toolbar mToolbar;
    private RecyclerView mCurrentRecycler;
    private RecyclerView mCompletedRecycler;
    private TasksAdapter mCurrentAdapter;
    private TasksAdapter mCompletedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        setHasOptionsMenu(true);

        mRepo = Initializer.getTasksLocal();

        mCurrentRecycler = view.findViewById(R.id.recyclerView_current_tasks);
        mCurrentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCompletedRecycler = view.findViewById(R.id.recyclerView_completed_tasks);
        mCompletedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI(){
        if(mCurrentAdapter == null){
            mCurrentAdapter = new TasksAdapter(mRepo.query(new GetCurrentTasksSpecification()));
        }
        if(mCompletedAdapter == null){
            mCompletedAdapter = new TasksAdapter(mRepo.query(new GetCompletedTaskSpecification()));
        }

        mCurrentRecycler.setAdapter(mCurrentAdapter);
        mCompletedRecycler.setAdapter(mCompletedAdapter);
        mCurrentAdapter.setTasks(mRepo.query(new GetCurrentTasksSpecification()));
        mCompletedAdapter.setTasks(mRepo.query(new GetCompletedTaskSpecification()));
        mCurrentAdapter.notifyDataSetChanged();
        mCompletedAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_today, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fragment_today_add:{
                Log.i("TodayFragment", "Add item");
                Intent intent = TaskDetailsActivity.newIntent(getContext(), null);
                startActivity(intent);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        Log.i("fragmentToday", "OnEditClick");
    }
}
