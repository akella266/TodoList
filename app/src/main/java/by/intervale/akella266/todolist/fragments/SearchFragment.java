package by.intervale.akella266.todolist.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.rx.RxSearchObservable;
import by.intervale.akella266.todolist.data.models.TaskItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment
        implements Observer{

    private Toolbar mToolbar;
    private LinearLayout mFrameSearch;
    private TextView mNoResult;
    private ToggleButton mBtnActive;
    private ToggleButton mBtnCompleted;
    private RecyclerView mRecyclerView;
    private TasksAdapter mAdapter;
    private LinearLayout mViewFilter;
    private List<TaskItem> mListTasks;
    private LinearLayout mSearchPanel;
    private SearchView mSearch;
    private Button mCancel;
    private boolean isSearching;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mToolbar = ((AppCompatActivity)getActivity()).findViewById(R.id.toolbar);
        mFrameSearch = view.findViewById(R.id.linear_layout_button_search);
        mNoResult = view.findViewById(R.id.textview_no_result);
        mBtnActive = view.findViewById(R.id.button_search_active);
        mBtnCompleted = view.findViewById(R.id.button_search_completed);
        mViewFilter = view.findViewById(R.id.linear_layout_search_filter);
        mRecyclerView = view.findViewById(R.id.recycler_search);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        mSearchPanel = view.findViewById(R.id.linear_layout_search_pannel);
        mCancel = view.findViewById(R.id.button_search_cancel);
        mSearch = view.findViewById(R.id.search_view);
        isSearching = false;

        mSearchPanel.setVisibility(GONE);
        mViewFilter.setVisibility(GONE);

        setUpToggles();
        setUpListeners();
        setUpSearch();
        updateUI();
        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (isSearching) displaySearching();
        else hideSearching();
    }

    private void setUpToggles() {
        mBtnActive.setTextColor(getResources().getColor(R.color.colorPrimary));
        mBtnCompleted.setTextColor(getResources().getColor(R.color.colorAccent));
        mBtnActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !mBtnCompleted.isChecked())
                    mBtnActive.setChecked(true);
                else if (b) {
                    mBtnActive.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mBtnCompleted.setChecked(false);
                    updateUI();
                }
                else
                    mBtnActive.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        mBtnCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b && !mBtnActive.isChecked())
                    mBtnCompleted.setChecked(true);
                else if (b) {
                    mBtnCompleted.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mBtnActive.setChecked(false);
                    updateUI();
                }
                else
                    mBtnCompleted.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

    @SuppressLint("CheckResult")
    private void setUpSearch() {
        RxSearchObservable.fromView(mSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskItem>>() {
                    @Override
                    public void accept(List<TaskItem> taskItems) throws Exception {
                        if (taskItems.isEmpty()){
                            mNoResult.setVisibility(VISIBLE);
                            mRecyclerView.setVisibility(GONE);
                        }
                        else{
                            mListTasks.clear();
                            mListTasks.addAll(taskItems);
                            updateUI();
                        }

                    }
                });
    }

    private void setUpListeners(){
        mFrameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySearching();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSearching();
            }
        });

        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hideSearching();
                return true;
            }
        });
    }

    private List<TaskItem> getRequestedTasks(){
        List<TaskItem> filterItems = new ArrayList<>();
        for(int i = 0; i < mListTasks.size(); i++){
            TaskItem currItem = mListTasks.get(i);
            if ((mBtnActive.isChecked() && !currItem.isComplete()) ||
                    (mBtnCompleted.isChecked() && currItem.isComplete()))
                filterItems.add(currItem);
        }
        return filterItems;
    }

    private void updateUI(){
        if (mAdapter == null){
            mListTasks = new ArrayList<>();
            mAdapter = new TasksAdapter(mListTasks);
        }
        if(mRecyclerView.getAdapter() == null) mRecyclerView.setAdapter(mAdapter);

        mAdapter.setTasks(getRequestedTasks());

        if(mAdapter.getItemCount() != 0) {
            mNoResult.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }

        mAdapter.notifyDataSetChanged();
    }

    private void displaySearching(){
        mToolbar.setVisibility(GONE);
        mFrameSearch.setVisibility(GONE);
        mSearchPanel.setVisibility(VISIBLE);
        mViewFilter.setVisibility(VISIBLE);

        mSearch.setFocusable(true);
        mSearch.setIconified(false);
        mSearch.requestFocusFromTouch();
        isSearching = true;
    }

    private void hideSearching(){
        mSearchPanel.setVisibility(GONE);
        mFrameSearch.setVisibility(VISIBLE);
        mToolbar.setVisibility(VISIBLE);
        mViewFilter.setVisibility(GONE);
        isSearching = false;
    }

}
