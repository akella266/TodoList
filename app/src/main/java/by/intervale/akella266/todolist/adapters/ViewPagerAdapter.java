package by.intervale.akella266.todolist.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.intervale.akella266.todolist.data.models.TaskItem;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private FragmentObserver mObservable;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mObservable = new FragmentObserver();
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mTitles.add(title);
        if (fragment instanceof Observer)
            mObservable.addObserver((Observer)fragment);
    }

    public void updateFragments(List<TaskItem> items){
        if (items == null) mObservable.notifyObservers();
        else mObservable.notifyObservers(items);
    }

    private class FragmentObserver extends Observable{

        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }

        @Override
        public void notifyObservers() {
            setChanged();
            super.notifyObservers();
        }
    }
}
