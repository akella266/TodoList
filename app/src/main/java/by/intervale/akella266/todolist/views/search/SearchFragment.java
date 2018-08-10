package by.intervale.akella266.todolist.views.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;
import by.intervale.akella266.todolist.rx.RxSearchObservable;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemClickListener;
import by.intervale.akella266.todolist.views.TypeData;
import by.intervale.akella266.todolist.views.inbox.InboxRecyclerFragment;
import by.intervale.akella266.todolist.views.inbox.InboxRecyclerPresenter;
import by.intervale.akella266.todolist.views.inbox.OnItemChangedListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment implements SearchContract.View{

    private Unbinder unbinder;
    private SearchContract.Presenter mPresenter;

    @BindView(R.id.textview_no_result)
    TextView mNoResult;
    @BindView(R.id.tablayout_search)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_search)
    ViewPager mPager;
    private SearchView mSearchView;
    private List<SearchRecyclerPresenter> mRecyclerPresenters;

    public SearchFragment() {}

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerPresenters = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        SearchRecyclerFragment fragmentFirst = SearchRecyclerFragment.newInstance();
        SearchRecyclerPresenter presenterFirst =
                new SearchRecyclerPresenter(fragmentFirst, TypeData.ACTIVE, onItemChangedListener);
        mRecyclerPresenters.add(presenterFirst);
        fragmentFirst.setPresenter(presenterFirst);
        viewPagerAdapter.addFragment(fragmentFirst, getContext().getString(R.string.tab_search_active));

        SearchRecyclerFragment fragmentSecond = SearchRecyclerFragment.newInstance();
        SearchRecyclerPresenter presenterSecond =
                new SearchRecyclerPresenter(fragmentSecond, TypeData.COMPLETED, onItemChangedListener);
        mRecyclerPresenters.add(presenterSecond);
        fragmentSecond.setPresenter(presenterSecond);
        viewPagerAdapter.addFragment(fragmentSecond, getContext().getString(R.string.completed_tasks));

        mPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.removeSearchListener();
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
                mPresenter.setUpSearchListener(mSearchView);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mPresenter.removeSearchListener();
                return true;
            }
        });
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showNoResults() {
        mNoResult.setVisibility(VISIBLE);
        mPager.setVisibility(View.INVISIBLE);
        mTabLayout.setVisibility(GONE);
    }

    @Override
    public void showResults(List<TaskItem> items) {
        mNoResult.setVisibility(GONE);
        mPager.setVisibility(VISIBLE);
        mTabLayout.setVisibility(VISIBLE);
        onItemChangedListener.onItemChanged();
    }

    private OnItemChangedListener onItemChangedListener = new OnItemChangedListener() {
        @Override
        public void onItemChanged() {
            for(SearchRecyclerPresenter presenter : mRecyclerPresenters)
                presenter.loadTasks(mPresenter.getRequestedTasks());
        }
    };
}
