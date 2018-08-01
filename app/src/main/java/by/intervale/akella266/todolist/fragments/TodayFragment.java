package by.intervale.akella266.todolist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.InboxItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.utils.ItemTouchActions;

public class TodayFragment extends Fragment
        implements View.OnClickListener, Observer{

    private CommonAdapter mAdapter;
    private RecyclerView mRecycler;
    private boolean isEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        setHasOptionsMenu(true);

        mRecycler = view.findViewById(R.id.today_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        isEdit = false;

        updateUI();
        return view;
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new CommonAdapter(getContext(),2,
                    new ItemTouchActions() {
                        @Override
                        public void onLeftClick(RecyclerView.Adapter ad, int position) {
                            TaskItem taskItem = ((TasksAdapter)ad).getTasks().get(position);
                            taskItem.setComplete(true);
                            Initializer.getTasksLocal().update(taskItem);
                            updateUI();
                        }

                        @Override
                        public void onRightClick(RecyclerView.Adapter ad,int position) {
                            TaskItem taskItem = ((TasksAdapter)ad).getTasks().remove(position);
                            Initializer.getTasksLocal().remove(taskItem);
                            updateUI();
                        }
                    });
        }
        if (mRecycler.getAdapter() == null){
            mRecycler.setAdapter(mAdapter);
        }
        mAdapter.setList(getItems());
        mAdapter.notifyDataSetChanged();
    }

    private List<InboxItem> getItems(){
        List<InboxItem> items = new ArrayList<>();
        items.add(new InboxItem("",
                Initializer.getTasksLocal().query(new GetCurrentTasksSpecification())));
        items.add(new InboxItem("Completed",
                Initializer.getTasksLocal().query(new GetCompletedTaskSpecification())));
        return items;
    }

    private void changeStateEditing(){
        if(isEdit){
            mAdapter.setEdit(false);
            isEdit = false;
        }
        else {
            mAdapter.setEdit(true);
            isEdit = true;
        }
    }

    private void resetStateEditinig(){
        if(isEdit){
            mAdapter.setEdit(false);
            isEdit = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_today, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fragment_today_add:{
                Intent intent = TaskDetailsActivity.newIntent(getContext(), null);
                startActivity(intent);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        changeStateEditing();
        updateUI();
    }

    @Override
    public void update(Observable observable, Object o) {
        resetStateEditinig();
        updateUI();
    }
}
