package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;

public class GroupDetailsFragment extends Fragment {

    public static final String ARGUMENT_DETAILS_GROUP = "bundle.details.group";

    private Unbinder unbinder;
    private Group mGroup;
    @BindView(R.id.edittext_group_details_name)
    EditText mName;
    private boolean isEdit;

    public static GroupDetailsFragment newInstance(Group group){
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_DETAILS_GROUP, group);
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
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if (getArguments() != null)
            mGroup = (Group) getArguments().getSerializable(ARGUMENT_DETAILS_GROUP);

        if (mGroup == null){
            isEdit = false;
            mGroup = new Group();
        }
        else isEdit = true;

        mName.setText(mGroup.getName());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fragment_details_done:{
                String title;
                if ((title = mName.getText().toString()).isEmpty()){
                    Snackbar.make(mName, getString(R.string.error_no_title), Snackbar.LENGTH_SHORT)
                            .show();
                    return false;
                }
                mGroup.setName(title);
                if(isEdit) Initializer.getGroupsLocal().update(mGroup);
                else Initializer.getGroupsLocal().add(mGroup);
                getActivity().finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
