package by.intervale.akella266.todolist.views.inbox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.views.TypeData;
import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;

public class InboxFragment extends Fragment{

    private Unbinder unbinder;

    @BindView(R.id.tablayout_inbox)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_inbox)
    ViewPager mPager;
    private List<RecyclerPresenter> mRecyclerPresenters;

    public InboxFragment() {}

    public static InboxFragment newInstance(){return new InboxFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerPresenters = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        RecyclerFragment fragmentFirst = RecyclerFragment.newInstance();
        RecyclerPresenter presenterFirst =
                new RecyclerPresenter(fragmentFirst, TypeData.DATE, onItemChangedListener);
        mRecyclerPresenters.add(presenterFirst);
        fragmentFirst.setPresenter(presenterFirst);
        viewPagerAdapter.addFragment(fragmentFirst, getContext().getString(R.string.inbox_date));

        RecyclerFragment fragmentSecond = RecyclerFragment.newInstance();
        RecyclerPresenter presenterSecond =
                new RecyclerPresenter(fragmentSecond, TypeData.GROUP, onItemChangedListener);
        mRecyclerPresenters.add(presenterSecond);
        fragmentSecond.setPresenter(presenterSecond);
        viewPagerAdapter.addFragment(fragmentSecond, getContext().getString(R.string.inbox_group));

        mPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private OnItemChangedListener onItemChangedListener = new OnItemChangedListener() {
        @Override
        public void onItemChanged() {
            for(RecyclerPresenter presenter : mRecyclerPresenters)
                presenter.loadTasks();
        }
    };
}
