package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemClickListener;

public class InboxFragment extends Fragment{

    private Unbinder unbinder;

    @BindView(R.id.tablayout_inbox)
    TabLayout mTabLayout;
    @BindView(R.id.recycler_inbox)
    RecyclerView mRecycler;
    private CommonAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateUI();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        updateUI();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new CommonAdapter(getContext(), new OnPopupMenuItemClickListener() {
                @Override
                public void onItemClick(TaskItem item) {
                    item.setComplete(true);
                    Initializer.getTasksLocal().update(item);
                    updateUI();
                }
            });
        }

        if(mRecycler.getAdapter() == null) mRecycler.setAdapter(mAdapter);

        mAdapter.setList(getTasksBy());
        mAdapter.notifyDataSetChanged();
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
        switch (mTabLayout.getSelectedTabPosition()){
            case 0:{
                return dateToString(taskItem.getDate());
            }
            case 1:{
                return Initializer.getGroupsLocal().query(
                        new GetGroupNameByIdSpecification(taskItem.getGroupId())).get(0).getName();
            }
            default:
                return "";
        }
    }

    private String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(date);
    }

}
