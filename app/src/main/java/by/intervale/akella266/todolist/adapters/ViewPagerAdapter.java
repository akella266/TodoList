package by.intervale.akella266.todolist.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import by.intervale.akella266.todolist.utils.FragmentObserver;


public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private FragmentObserver mObserver;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mObserver = new FragmentObserver();
    }

    @Override
    public Fragment getItem(int position) {
        mObserver.deleteObservers();
        Fragment fragment = mFragments.get(position);
        if (fragment instanceof Observer) mObserver.addObserver((Observer)fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mTitles.add(title);
    }

    public void updateFragments(){
        mObserver.notifyObservers();
    }
}
