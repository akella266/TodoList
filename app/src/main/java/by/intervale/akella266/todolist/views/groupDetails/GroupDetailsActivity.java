package by.intervale.akella266.todolist.views.groupDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;

public class GroupDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_DATA = "akella299.intent.group_data";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static Intent getStartIntent(Context context, UUID itemId){
        Intent intent = new Intent(context, GroupDetailsActivity.class);
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
        GroupDetailsFragment fragment = GroupDetailsFragment.newInstance();
        fragment.setPresenter(new GroupDetailsPresenter(fragment, itemId));
        if (itemId != null) mToolbar.setTitle(R.string.title_edit_group);
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
