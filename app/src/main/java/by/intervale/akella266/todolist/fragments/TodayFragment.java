package by.intervale.akella266.todolist.fragments;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.utils.ItemTouchActions;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class TodayFragment extends Fragment
        implements OnToolbarButtonsClickListener, Observer{

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

        mRecycler = view.findViewById(R.id.recycler_today);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        isEdit = false;

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetStateEditing();
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
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    private void updateUI(){
        if(mAdapter == null)
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

        if (mRecycler.getAdapter() == null) mRecycler.setAdapter(mAdapter);
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

    private void changeStateEditing(View view){
        if(isEdit){
            mAdapter.setEdit(false);
            ((TextView)view).setText(getString(R.string.toolbar_button_edit));
            isEdit = false;
        }
        else {
            mAdapter.setEdit(true);
            ((TextView)view).setText(getString(R.string.toolbar_button_done));
            isEdit = true;
        }
    }

    private void resetStateEditing(){
        if(isEdit){
            mAdapter.setEdit(false);
            isEdit = false;
            mRecycler.setAdapter(null);
        }
    }
}
