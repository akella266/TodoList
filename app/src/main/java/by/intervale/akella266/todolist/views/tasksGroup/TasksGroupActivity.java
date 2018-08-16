package by.intervale.akella266.todolist.views.tasksGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;

public class TasksGroupActivity extends AppCompatActivity {

    public static final String EXTRA_GROUP = "akella266.intent.group";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context, Group group){
        Intent intent = new Intent(context, TasksGroupActivity.class);
        intent.putExtra(EXTRA_GROUP, group);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_group);
        ButterKnife.bind(this);
        Group group = (Group) getIntent().getSerializableExtra(EXTRA_GROUP);
        toolbar.setTitle(group.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TasksGroupFragment fragment = TasksGroupFragment.newInstance();
        fragment.setPresenter(new TasksGroupPresenter(this, fragment, group.getIdUUID()));
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
