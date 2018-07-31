package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.InboxAdapter;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.InboxItem;
import by.intervale.akella266.todolist.utils.Initializer;

public class InboxFragment extends Fragment
        implements View.OnClickListener{

    private ToggleButton mBtnDate;
    private ToggleButton mBtnGroup;
    private RecyclerView mRecycler;
    private InboxAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        mBtnDate = view.findViewById(R.id.inbox_btn_active);
        mBtnGroup = view.findViewById(R.id.inbox_btn_completed);
        mRecycler = view.findViewById(R.id.inbox_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpToggles();
        updateUI();
        return view;
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new InboxAdapter(getContext());
            mRecycler.setAdapter(mAdapter);
        }

        Comparator<TaskItem> comparator;
        if (mBtnDate.isChecked()) {
            comparator = new Comparator<TaskItem>() {
                @Override
                public int compare(TaskItem taskItem, TaskItem t1) {
                    return dateToString(taskItem.getDate())
                            .compareTo(dateToString(t1.getDate()));
                }
            };
        }
        else{
            comparator = new Comparator<TaskItem>() {
                @Override
                public int compare(TaskItem taskItem, TaskItem t1) {
                    return taskItem.getGroupId().compareTo(t1.getGroupId());
                }
            };
        }
        mAdapter.setList(getTasksBy(comparator));
        mAdapter.notifyDataSetChanged();
    }

    private void setUpToggles() {
        mBtnDate.setTextColor(getResources().getColor(R.color.colorPrimary));
        mBtnGroup.setTextColor(getResources().getColor(R.color.colorAccent));
        mBtnDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !mBtnGroup.isChecked())
                    mBtnDate.setChecked(true);
                else if (b) {
                    mBtnDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mBtnGroup.setChecked(false);
                    updateUI();
                }
                else
                    mBtnDate.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        mBtnGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !mBtnDate.isChecked())
                    mBtnGroup.setChecked(true);
                else if (b) {
                    mBtnGroup.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mBtnDate.setChecked(false);
                    updateUI();
                }
                else
                    mBtnGroup.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

    @Override
    public void onClick(View view) {}

    public List<InboxItem> getTasksBy(Comparator<TaskItem> comparator) {
        List<InboxItem> items = new ArrayList<>();
        List<TaskItem> currentTasks = Initializer.getTasksLocal().query(new GetCurrentTasksSpecification());
        Collections.sort(currentTasks);
        List<TaskItem> innerList = new ArrayList<>();
        for(int i = 1; i < currentTasks.size(); i++){
            innerList.add(currentTasks.get(i-1));
            if (comparator.compare(currentTasks.get(i-1), currentTasks.get(i)) != 0){
                items.add(new InboxItem(getNameCategory(currentTasks.get(i-1)),
                        innerList));
                innerList = new ArrayList<>();
            }
        }
        innerList.add(currentTasks.get(currentTasks.size()-1));
        items.add(new InboxItem(getNameCategory(currentTasks.get(currentTasks.size()-1)),
                innerList));
        return items;
    }

    private String getNameCategory(TaskItem taskItem){
        return mBtnDate.isChecked() ? dateToString(taskItem.getDate())
                : Initializer.getGroupsLocal().query(
                        new GetGroupNameByIdSpecification(taskItem.getGroupId())).get(0).getName();
    }

    public String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(date);
    }
}
