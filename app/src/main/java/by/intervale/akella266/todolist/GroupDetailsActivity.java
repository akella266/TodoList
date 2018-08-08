package by.intervale.akella266.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.fragments.GroupDetailsFragment;

public class GroupDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.group_data";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static Intent getStartIntent(Context context, Group item){
        Intent intent = new Intent(context, GroupDetailsActivity.class);
        intent.putExtra(EXTRA_ADD_DATA, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Group item = (Group) intent.getSerializableExtra(EXTRA_ADD_DATA);
        GroupDetailsFragment fragment = GroupDetailsFragment.newInstance(item);
        if (item != null) mToolbar.setTitle(R.string.title_edit_group);
        else mToolbar.setTitle(R.string.title_add_group);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
