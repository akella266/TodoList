package by.intervale.akella266.todolist.views.inbox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public InboxFragment() {}

    public static InboxFragment newInstance(){return new InboxFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        unbinder = ButterKnife.bind(this, view);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        InboxRecyclerFragment fragmentFirst = InboxRecyclerFragment.newInstance();
        fragmentFirst.setPresenter(new InboxRecyclerPresenter(getContext(),fragmentFirst, TypeData.DATE,
                true, onItemChangedListener));
        viewPagerAdapter.addFragment(fragmentFirst, getContext().getString(R.string.inbox_date));

        InboxRecyclerFragment fragmentSecond = InboxRecyclerFragment.newInstance();
        fragmentSecond.setPresenter(new InboxRecyclerPresenter(getContext(), fragmentSecond, TypeData.GROUP,
                true, onItemChangedListener));
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
            ((ViewPagerAdapter)mPager.getAdapter()).updateFragments(null);
        }
    };
}
