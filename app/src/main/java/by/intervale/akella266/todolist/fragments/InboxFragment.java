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
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.ItemTouchActions;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class InboxFragment extends Fragment
        implements OnToolbarButtonsClickListener, Observer{

    private ToggleButton mBtnDate;
    private ToggleButton mBtnGroup;
    private RecyclerView mRecycler;
    private CommonAdapter mAdapter;
    private boolean isEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        isEdit = false;
        mBtnDate = view.findViewById(R.id.button_inbox_active);
        mBtnGroup = view.findViewById(R.id.button_inbox_completed);
        mRecycler = view.findViewById(R.id._recycler_inbox);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpToggles();
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetStateEditing();
        updateUI();
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new CommonAdapter(getContext(), 2, new ItemTouchActions() {
                @Override
                public void onLeftClick(RecyclerView.Adapter adapter, int position) {
                    TaskItem taskItem = ((TasksAdapter)adapter).getTasks().get(position);
                    taskItem.setComplete(true);
                    Initializer.getTasksLocal().update(taskItem);
                    updateUI();
                }

                @Override
                public void onRightClick(RecyclerView.Adapter adapter, int position) {
                    TaskItem taskItem = ((TasksAdapter)adapter).getTasks().remove(position);
                    Initializer.getTasksLocal().remove(taskItem);
                    updateUI();
                }
            });
        }

        if(mRecycler.getAdapter() == null) mRecycler.setAdapter(mAdapter);

        mAdapter.setList(getTasksBy());
        mAdapter.notifyDataSetChanged();
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

    private List<InboxItem> getTasksBy() {
        List<InboxItem> items = new ArrayList<>();
        Map<String, List<TaskItem>> map = new HashMap<>();
        List<TaskItem> currentTasks = Initializer.getTasksLocal().query(new GetCurrentTasksSpecification());
        Collections.sort(currentTasks);
        for(int i = 0; i < currentTasks.size(); i++){
            if (!map.containsKey(getNameCategory(currentTasks.get(i)))){
                map.put(getNameCategory(currentTasks.get(i)), new ArrayList<TaskItem>());
            }
            map.get(getNameCategory(currentTasks.get(i))).add(currentTasks.get(i));
        }

        for(Map.Entry<String, List<TaskItem>> entry : map.entrySet()){
            items.add(new InboxItem(entry.getKey(), entry.getValue()));
        }

        Collections.sort(items);
        return items;
    }

    private String getNameCategory(TaskItem taskItem){
        return mBtnDate.isChecked() ? dateToString(taskItem.getDate())
                : Initializer.getGroupsLocal().query(
                        new GetGroupNameByIdSpecification(taskItem.getGroupId())).get(0).getName();
    }

    private String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(date);
    }

}
