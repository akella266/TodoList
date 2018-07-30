package by.intervale.akella266.todolist.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
    }


    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mTitles.add(title);
        
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
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
}
