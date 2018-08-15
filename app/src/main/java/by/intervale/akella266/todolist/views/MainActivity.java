package by.intervale.akella266.todolist.views;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.views.inbox.InboxFragment;
import by.intervale.akella266.todolist.views.search.SearchFragment;
import by.intervale.akella266.todolist.views.todo.ToDoListFragment;
import by.intervale.akella266.todolist.views.search.SearchPresenter;
import by.intervale.akella266.todolist.views.today.TodayFragment;
import by.intervale.akella266.todolist.views.today.TodayPresenter;
import by.intervale.akella266.todolist.views.todo.TodoListPresenter;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    private final String SHARED_FIRST_LAUNCH = "first_launch";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        checkOnFirstLaunch();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_today);
        mTabs.setOnNavigationItemSelectedListener(this);
        TodayFragment fragment = new TodayFragment();
        fragment.setPresenter(new TodayPresenter(this, fragment));
        loadFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_today: {
                TodayFragment todayFragment = new TodayFragment();
                todayFragment.setPresenter(new TodayPresenter(this, todayFragment));
                getSupportActionBar().setTitle( getString(R.string.title_today));
                return loadFragment(todayFragment);
            }
            case R.id.menu_inbox:{
                InboxFragment inboxFragment = InboxFragment.newInstance();
                getSupportActionBar().setTitle(getString(R.string.title_inbox));
                return loadFragment(inboxFragment);
            }
            case R.id.menu_todolist:{
                ToDoListFragment toDoListFragment = new ToDoListFragment();
                toDoListFragment.setPresenter(new TodoListPresenter(this, toDoListFragment));
                getSupportActionBar().setTitle(getString(R.string.title_todolist));
                return loadFragment(toDoListFragment);
            }
            case R.id.menu_search:{
                SearchFragment searchFragment = SearchFragment.newInstance();
                searchFragment.setPresenter(new SearchPresenter(this, searchFragment));
                getSupportActionBar().setTitle(getString(R.string.title_search));
                return loadFragment(searchFragment);
            }
            default:return false;
        }
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void checkOnFirstLaunch(){
        SharedPreferences sp = getSharedPreferences("SharedFirst", MODE_PRIVATE);
        if (sp.getBoolean(SHARED_FIRST_LAUNCH, true)){
            Initializer.initialize(this);
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean(SHARED_FIRST_LAUNCH, false);
            ed.commit();
        }
    }
}
