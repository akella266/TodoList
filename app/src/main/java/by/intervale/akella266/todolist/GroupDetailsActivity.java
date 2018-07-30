package by.intervale.akella266.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.fragments.GroupDetailsFragment;

public class GroupDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.group_data";

    public static Intent newIntent(Context context, Group item){
        Intent intent = new Intent(context, GroupDetailsActivity.class);
        intent.putExtra(EXTRA_ADD_DATA, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        toolbar.setTitle(R.string.group_details_title_add_project);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent intent = getIntent();
        Group item = (Group) intent.getSerializableExtra(EXTRA_ADD_DATA);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, GroupDetailsFragment.getInstance(item))
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
