package by.intervale.akella266.todolist;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.fragments.InboxFragment;
import by.intervale.akella266.todolist.fragments.SearchFragment;
import by.intervale.akella266.todolist.fragments.ToDoListFragment;
import by.intervale.akella266.todolist.fragments.TodayFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_today);
        mTabs.setOnNavigationItemSelectedListener(this);
        loadFragment(new TodayFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment currentFragment =null;
        String title = "";
        switch (item.getItemId()){
            case R.id.menu_today: {
                title = getString(R.string.title_today);
                currentFragment = new TodayFragment();
                break;
            }
            case R.id.menu_inbox:{
                title = getString(R.string.title_inbox);
                currentFragment = new InboxFragment();
                break;
            }
            case R.id.menu_todolist:{
                title = getString(R.string.title_todolist);
                currentFragment = new ToDoListFragment();
                break;
            }
            case R.id.menu_search:{
                title = getString(R.string.title_search);
                currentFragment = new SearchFragment();
                break;
            }
        }
        getSupportActionBar().setTitle(title);
        return loadFragment(currentFragment);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
