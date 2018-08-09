package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.rx.RxSearchObservable;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment{

    private Unbinder unbinder;

    @BindView(R.id.textview_no_result)
    TextView mNoResult;
    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.tablayout_search)
    TabLayout mTab;
    private TasksAdapter mAdapter;
    private List<TaskItem> mListTasks;
    private SearchView mSearchView;
    private Disposable mDisposable;
    private boolean typeTask;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        typeTask = true;
                        mAdapter.update(getRequestedTasks());
                        break;
                    }
                    case 1:{
                        typeTask = false;
                        mAdapter.update(getRequestedTasks());
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mTab.setVisibility(GONE);

        typeTask = true;
        updateUI();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        removeSearchListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setUpSearchListener();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                removeSearchListener();
                return true;
            }
        });
    }

    private void setUpSearchListener() {
        mDisposable = RxSearchObservable.fromView(mSearchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskItem>>() {
                    @Override
                    public void accept(List<TaskItem> taskItems){
                        if (taskItems.isEmpty()){
                            mNoResult.setVisibility(VISIBLE);
                            mRecyclerView.setVisibility(GONE);
                            mTab.setVisibility(GONE);
                        }
                        else{
                            mListTasks.clear();
                            mListTasks.addAll(taskItems);
                            mAdapter.update(getRequestedTasks());
                            updateUI();
                        }

                    }
                });
    }

    private void removeSearchListener(){
        if (mDisposable != null)
            mDisposable.dispose();
    }

    private List<TaskItem> getRequestedTasks(){
        List<TaskItem> filterItems = new ArrayList<>();
        for(int i = 0; i < mListTasks.size(); i++){
            TaskItem currItem = mListTasks.get(i);
            if ((typeTask && !currItem.isComplete()) ||
                    (!typeTask && currItem.isComplete()))
                filterItems.add(currItem);
        }
        return filterItems;
    }

    private void updateUI(){
        if (mAdapter == null){
            mListTasks = new ArrayList<>();
            mAdapter = new TasksAdapter(getContext(),
                    new ArrayList<TaskItem>(), null
//                    new OnPopupMenuItemClickListener() {
//                        @Override
//                        public void onItemClick(TaskItem item) {
//                            item.setComplete(true);
//                            Initializer.getTasksLocal().update(item);
//                            mAdapter.update(getRequestedTasks());
//                        }
//                    }
                    );
        }
        if(mRecyclerView.getAdapter() == null) mRecyclerView.setAdapter(mAdapter);

        if(mAdapter.getItemCount() != 0) {
            mNoResult.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
            mTab.setVisibility(VISIBLE);
        }
    }


}
