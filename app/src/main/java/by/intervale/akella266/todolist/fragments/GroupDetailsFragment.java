package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;

public class GroupDetailsFragment extends Fragment
        implements OnToolbarButtonsClickListener{

    public static final String ARGS_DETAILS_GROUP = "bundle.details.group";

    private Group mGroup;
    private EditText mName;
    private boolean isEdit;

    public static GroupDetailsFragment getInstance(Group group){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DETAILS_GROUP, group);
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_details, container, false);


        if (getArguments() != null)
            mGroup = (Group) getArguments().getSerializable(ARGS_DETAILS_GROUP);

        if (mGroup == null){
            isEdit = false;
            mGroup = new Group();
        }
        else isEdit = true;

        mName = view.findViewById(R.id.group_details_name);
        mName.setText(mGroup.getName());
        return view;
    }

    @Override
    public void onLeftButtonClick(View view) {
        getActivity().finish();
    }

    @Override
    public void onRightButtonClick(View view) {
        String title;
        if ((title = mName.getText().toString()).isEmpty()){
            Snackbar.make(mName, getString(R.string.error_no_title), Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        mGroup.setName(title);
        if(isEdit) Initializer.getGroupsLocal().update(mGroup);
        else Initializer.getGroupsLocal().add(mGroup);
        getActivity().finish();
    }
}
