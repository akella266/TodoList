package by.intervale.akella266.todolist.views.taskDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.task_data";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getStartIntent(Context context, UUID itemId){
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(EXTRA_ADD_DATA, itemId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        UUID itemId = (UUID) intent.getSerializableExtra(EXTRA_ADD_DATA);
        final TaskDetailsFragment fragment = TaskDetailsFragment.newInstance();
        fragment.setPresenter(new TaskDetailsPresenter(fragment, itemId));
        if (itemId == null) mToolbar.setTitle(R.string.title_add_task);
        else mToolbar.setTitle(R.string.title_edit_task);
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
