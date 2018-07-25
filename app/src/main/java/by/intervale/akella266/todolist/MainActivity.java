package by.intervale.akella266.todolist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import by.intervale.akella266.todolist.adapters.ViewPagerAdapter;
import by.intervale.akella266.todolist.fragments.FragmentInbox;
import by.intervale.akella266.todolist.fragments.FragmentSearch;
import by.intervale.akella266.todolist.fragments.FragmentToDoList;
import by.intervale.akella266.todolist.fragments.FragmentToday;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTabs();
    }

    private void initTabs(){
        TabLayout tabs = (TabLayout)findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentToday(), getString(R.string.title_today));
        viewPagerAdapter.addFragment(new FragmentInbox(), getString(R.string.title_inbox));
        viewPagerAdapter.addFragment(new FragmentToDoList(), getString(R.string.title_todolist));
        viewPagerAdapter.addFragment(new FragmentSearch(), getString(R.string.title_search));

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(viewPager);
    }


}
