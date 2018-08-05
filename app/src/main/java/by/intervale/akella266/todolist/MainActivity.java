package by.intervale.akella266.todolist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;
import by.intervale.akella266.todolist.fragments.InboxFragment;
import by.intervale.akella266.todolist.fragments.SearchFragment;
import by.intervale.akella266.todolist.fragments.ToDoListFragment;
import by.intervale.akella266.todolist.fragments.TodayFragment;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private Toolbar mToolbar;
    private TextView mTitleToolbar;
    private TextView mLeftToolbarButton;
    private TextView mRightToolbarButton;
    private TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabs();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) super.onBackPressed();
        else mPager.setCurrentItem(0);
    }

    private void initToolbar(){
        mToolbar = findViewById(R.id.toolbar);
        mLeftToolbarButton = mToolbar.findViewById(R.id.textview_toolbar_left_button);
        mRightToolbarButton = mToolbar.findViewById(R.id.textview_toolbar_right_button);
        mRightToolbarButton.setCompoundDrawablesWithIntrinsicBounds(0,
                0,
                R.drawable.ic_add,
                0);
        mTitleToolbar = mToolbar.findViewById(R.id.textview_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void initTabs(){
        mTabs = findViewById(R.id.tabLayout);

        mPager = findViewById(R.id.viewPager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TodayFragment(), getString(R.string.title_today));
        viewPagerAdapter.addFragment(new InboxFragment(), getString(R.string.title_inbox));
        viewPagerAdapter.addFragment(new ToDoListFragment(), getString(R.string.title_todolist));
        viewPagerAdapter.addFragment(new SearchFragment(), getString(R.string.title_search));

        mPager.setAdapter(viewPagerAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mTabs.setupWithViewPager(mPager);
        setUpFirstTab(viewPagerAdapter);
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()){
                    case 0: {
                        configToolbar((TodayFragment)viewPagerAdapter.getItem(tab.getPosition()),
                                getString(R.string.title_today),
                                false);
                        viewPagerAdapter.updateFragments();
                        break;
                    }
                    case 1:{
                        configToolbar((InboxFragment)viewPagerAdapter.getItem(tab.getPosition()),
                                getString(R.string.title_inbox),
                                false);
                        viewPagerAdapter.updateFragments();
                        break;
                    }
                    case 2:{
                        configToolbar((ToDoListFragment)viewPagerAdapter.getItem(tab.getPosition()),
                                getString(R.string.title_todolist),
                                false);
                        viewPagerAdapter.updateFragments();
                        break;
                    }
                    case 3:{
                        configToolbar(null,getString(R.string.title_search),true);
                        viewPagerAdapter.getItem(3);
                        viewPagerAdapter.updateFragments();
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
        setUpIcons();
    }

    private void setUpFirstTab(ViewPagerAdapter viewPagerAdapter){
       configToolbar((TodayFragment)viewPagerAdapter.getItem(0),
               getString(R.string.title_today),
               false);
    }

    private void setUpIcons(){
        mTabs.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_tab_today));
        mTabs.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_tab_inbox));
        mTabs.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_tab_todolist));
        mTabs.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_tab_search));
    }

    private void configToolbar(final OnToolbarButtonsClickListener clickListener,
                                    String title,
                                    boolean hideButtons){
        if (mToolbar.getVisibility() == View.GONE) mToolbar.setVisibility(View.VISIBLE);
        mTitleToolbar.setText(title);
        mLeftToolbarButton.setText(getString(R.string.toolbar_button_edit));
        if (hideButtons){
            mLeftToolbarButton.setVisibility(View.GONE);
            mRightToolbarButton.setVisibility(View.GONE);
        }
        else {
            mLeftToolbarButton.setVisibility(View.VISIBLE);
            mRightToolbarButton.setVisibility(View.VISIBLE);
            mLeftToolbarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onLeftButtonClick(mLeftToolbarButton);
                }
            });
            mRightToolbarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRightButtonClick(mRightToolbarButton);
                }
            });
        }
    }



}
