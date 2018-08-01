package by.intervale.akella266.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import by.intervale.akella266.todolist.fragments.TaskDetailsFragment;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

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

        Intent intent = getIntent();
        TaskItem item = (TaskItem) intent.getSerializableExtra(EXTRA_ADD_DATA);
        final TaskDetailsFragment fragment = TaskDetailsFragment.getInstance(item);
        if (item == null){
            initToolbar(fragment, false);
        }
        else{
            initToolbar(fragment, true);
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    private void initToolbar(final OnToolbarButtonsClickListener listener, boolean isEdit){
        Toolbar mToolbar = findViewById(R.id.toolbar);
        final TextView mLeftToolbarButton = mToolbar.findViewById(R.id.toolbar_left_button);
        mLeftToolbarButton.setText(R.string.button_cancel);
        mLeftToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLeftButtonClick(mLeftToolbarButton);
            }
        });
        final TextView mRightToolbarButton = mToolbar.findViewById(R.id.toolbar_right_button);
        mRightToolbarButton.setText(R.string.toolbar_button_done);
        mRightToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRightButtonClick(mRightToolbarButton);
            }
        });
        TextView mTitleToolbar = mToolbar.findViewById(R.id.toolbar_title);
        if (isEdit){
            mTitleToolbar.setText(R.string.title_edit_task);
        }
        else{
            mTitleToolbar.setText(R.string.title_add_task);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
