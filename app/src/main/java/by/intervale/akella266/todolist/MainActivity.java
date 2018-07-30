package by.intervale.akella266.todolist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;
import by.intervale.akella266.todolist.fragments.InboxFragment;
import by.intervale.akella266.todolist.fragments.SearchFragment;
import by.intervale.akella266.todolist.fragments.ToDoListFragment;
import by.intervale.akella266.todolist.fragments.TodayFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_edit);
        setSupportActionBar(mToolbar);
        setDisplayHomeUp(true);
        initTabs();
    }

    private void setDisplayHomeUp(boolean displayHomeUp){
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeUp);
        getSupportActionBar().setHomeButtonEnabled(displayHomeUp);
    }

    private void initTabs(){
        TabLayout tabs = findViewById(R.id.tabLayout);
        mPager = findViewById(R.id.viewPager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TodayFragment(), getString(R.string.title_today));
        viewPagerAdapter.addFragment(new InboxFragment(), getString(R.string.title_inbox));
        viewPagerAdapter.addFragment(new ToDoListFragment(), getString(R.string.title_todolist));
        viewPagerAdapter.addFragment(new SearchFragment(), getString(R.string.title_search));

        mPager.setAdapter(viewPagerAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(mPager);
        setUpFirstTab(viewPagerAdapter);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()){
                    case 0: {
                        mToolbar.setTitle(R.string.title_today);
                        mToolbar.setNavigationOnClickListener(
                                (TodayFragment)viewPagerAdapter.getItem(tab.getPosition()));
                        setDisplayHomeUp(true);
                        break;
                    }
                    case 1:{
                        mToolbar.setTitle(R.string.title_inbox);
                        setDisplayHomeUp(true);
                        break;
                    }
                    case 2:{
                        mToolbar.setTitle(R.string.title_todolist);
                        setDisplayHomeUp(true);
                        break;
                    }
                    case 3:{
                        mToolbar.setTitle(R.string.title_search);
                        setDisplayHomeUp(false);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpFirstTab(ViewPagerAdapter viewPagerAdapter){
        getSupportActionBar().setTitle(R.string.title_today);
        mToolbar.setNavigationOnClickListener(
                (TodayFragment)viewPagerAdapter.getItem(0));
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(0);
        }
    }

}
