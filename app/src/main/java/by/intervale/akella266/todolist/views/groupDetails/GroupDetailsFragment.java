package by.intervale.akella266.todolist.views.groupDetails;

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

public class GroupDetailsFragment extends Fragment
        implements GroupDetailsContract.View{

    private GroupDetailsContract.Presenter mPresenter;
    private Unbinder unbinder;
    @BindView(R.id.edittext_group_details_name)
    EditText mName;

    public static GroupDetailsFragment newInstance(){
        return new GroupDetailsFragment();
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
                mPresenter.saveGroup(mName.getText().toString());
                getActivity().finish();
            }
            case R.id.menu_fragment_details_remove:{
                mPresenter.removeGroup();
                getActivity().finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGroup(Group group) {
        mName.setText(group.getName());
    }

    @Override
    public void showError(int message) {
        Snackbar.make(mName, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setPresenter(GroupDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
