package by.intervale.akella266.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.fragments.GroupDetailsFragment;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class GroupDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.group_data";

    public static Intent getStartIntent(Context context, Group item){
        Intent intent = new Intent(context, GroupDetailsActivity.class);
        intent.putExtra(EXTRA_ADD_DATA, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        Group item = (Group) intent.getSerializableExtra(EXTRA_ADD_DATA);
        GroupDetailsFragment fragment = GroupDetailsFragment.newInstance(item);
        if (item == null) initToolbar(fragment, false);
        else initToolbar(fragment, true);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initToolbar(final OnToolbarButtonsClickListener listener, boolean isEdit){
        Toolbar mToolbar = findViewById(R.id.toolbar);
        final TextView mLeftToolbarButton = mToolbar.findViewById(R.id.textview_toolbar_left_button);
        mLeftToolbarButton.setText(R.string.button_cancel);
        mLeftToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLeftButtonClick(mLeftToolbarButton);
            }
        });
        final TextView mRightToolbarButton = mToolbar.findViewById(R.id.textview_toolbar_right_button);
        mRightToolbarButton.setText(R.string.toolbar_button_done);
        mRightToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRightButtonClick(mRightToolbarButton);
            }
        });
        TextView mTitleToolbar = mToolbar.findViewById(R.id.textview_toolbar_title);
        if (isEdit) mTitleToolbar.setText(R.string.title_edit_group);
        else mTitleToolbar.setText(R.string.title_add_group);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
