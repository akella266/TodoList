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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.adapters.CommonAdapter;
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemClickListener;

public class TodayFragment extends Fragment{

    private Unbinder unbinder;

    @BindView(R.id.recycler_today)
    RecyclerView mRecycler;
    private CommonAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

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

    @OnClick(R.id.fab_today)
    public void onFabClick(){
        Intent intent = TaskDetailsActivity.getStartIntent(getContext(), null);
        startActivity(intent);
    }

    private void updateUI(){
        if(mAdapter == null)
            mAdapter = new CommonAdapter(getContext(), new OnPopupMenuItemClickListener() {
                @Override
                public void onItemClick(TaskItem item) {
                    item.setComplete(true);
                    Initializer.getTasksLocal().update(item);
                    updateUI();
                }
            });
        if (mRecycler.getAdapter() == null)
            mRecycler.setAdapter(mAdapter);
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
}
