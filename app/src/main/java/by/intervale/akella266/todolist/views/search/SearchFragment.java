package by.intervale.akella266.todolist.views.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.views.TypeData;
import by.intervale.akella266.todolist.views.inbox.OnItemChangedListener;

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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        SearchRecyclerFragment fragmentFirst = SearchRecyclerFragment.newInstance();
        fragmentFirst.setPresenter(new SearchRecyclerPresenter(fragmentFirst, TypeData.ACTIVE, onItemChangedListener));
        viewPagerAdapter.addFragment(fragmentFirst, getContext().getString(R.string.tab_search_active));

        SearchRecyclerFragment fragmentSecond = SearchRecyclerFragment.newInstance();
        fragmentSecond.setPresenter( new SearchRecyclerPresenter(fragmentSecond, TypeData.COMPLETED, onItemChangedListener));
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
            ((ViewPagerAdapter)mPager.getAdapter()).updateFragments(mPresenter.getRequestedTasks());
        }
    };
}
