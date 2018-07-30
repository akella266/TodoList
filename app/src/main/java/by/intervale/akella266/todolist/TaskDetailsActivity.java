package by.intervale.akella266.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import by.intervale.akella266.todolist.fragments.TaskDetailsFragment;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.task_data";

    public static Intent newIntent(Context context, TaskItem item){
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(EXTRA_ADD_DATA, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        TaskItem item = (TaskItem) intent.getSerializableExtra(EXTRA_ADD_DATA);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, TaskDetailsFragment.getInstance(item))
                .addToBackStack(null).commit();
    }
}
