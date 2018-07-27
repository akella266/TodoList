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
import java.util.concurrent.TimeUnit;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.rx.RxSearchObservable;
import by.intervale.akella266.todolist.utils.TaskItem;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentSearch extends Fragment {

    private Toolbar mToolbar;
    private TextView mEdit;
    private Button mBtnSearch;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        configureToolbar();

        mBtnSearch = view.findViewById(R.id.search_btn_search);
        mNoResult = view.findViewById(R.id.search_no_result);
        mBtnActive = view.findViewById(R.id.search_btn_active);
        mBtnCompleted = view.findViewById(R.id.search_btn_completed);
        mViewFilter = view.findViewById(R.id.search_filter);
        mRecyclerView = view.findViewById(R.id.search_recycler);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        mSearchPanel = view.findViewById(R.id.search_pannel);
        mCancel = view.findViewById(R.id.search_btn_cancel);
        mSearch = view.findViewById(R.id.search_view);

        mSearchPanel.setVisibility(GONE);
        mViewFilter.setVisibility(GONE);

        setUpToggles();
        setUpListeners();
        setUpSearch();
        updateUI();
        return view;
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
//                .distinctUntilChanged()
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
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setVisibility(GONE);
                mBtnSearch.setVisibility(GONE);
                mSearchPanel.setVisibility(VISIBLE);
                mViewFilter.setVisibility(VISIBLE);

                mSearch.setFocusable(true);
                mSearch.setIconified(false);
                mSearch.requestFocusFromTouch();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchPanel.setVisibility(GONE);
                mBtnSearch.setVisibility(VISIBLE);
                mToolbar.setVisibility(VISIBLE);
                mViewFilter.setVisibility(GONE);
            }
        });

        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchPanel.setVisibility(GONE);
                mBtnSearch.setVisibility(VISIBLE);
                mToolbar.setVisibility(VISIBLE);
                mViewFilter.setVisibility(GONE);
                return true;
            }
        });
    }

    private List<TaskItem> getRequestedTasks(){
        List<TaskItem> filterItems = new ArrayList<>();
        for(int i = 0; i < mListTasks.size(); i++){
            TaskItem currItem = mListTasks.get(i);
            if ((mBtnActive.isChecked() && !currItem.isComplete()) ||
                    (mBtnCompleted.isChecked() && currItem.isComplete())) {
                filterItems.add(currItem);
            }
        }
        return filterItems;
    }

    private void updateUI(){
        if (mAdapter == null){
            mListTasks = new ArrayList<>();
            mAdapter = new TasksAdapter(mListTasks);
            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter.setTasks(getRequestedTasks());

        if(mAdapter.getItemCount() != 0) {
            mNoResult.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }

        mAdapter.notifyDataSetChanged();
    }

    private void configureToolbar(){
        AppCompatActivity activity;
        if ((activity = (AppCompatActivity)getActivity()) != null) {
            mToolbar = activity.findViewById(R.id.toolbar);
            mEdit = activity.findViewById(R.id.toolbar_additional_button);
            mEdit.setVisibility(GONE);
            TextView mTitle = activity.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.title_search);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mEdit.setVisibility(VISIBLE);
        mToolbar.setVisibility(VISIBLE);

    }
}
