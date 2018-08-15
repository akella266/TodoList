package by.intervale.akella266.todolist.views.taskDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.specifications.localJson.group.GetGroupByIdLocalSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.utils.Priority;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskDetailsFragment extends Fragment
        implements TaskDetailsContract.View,
        PriorityDialogFragment.DialogPriorityListener,
        GroupDialogFragment.DialogGroupListener {

    private Unbinder unbinder;
    private TaskDetailsContract.Presenter mPresenter;

    @BindView(R.id.edittext_details_title)
    EditText mTitle;
    @BindView(R.id.switch_details_remind)
    Switch mReminder;
    @BindView(R.id.textview_details_priority)
    TextView mPriority;
    @BindView(R.id.edittext_details_notes)
    EditText mNotes;
    @BindView(R.id.textview_details_date)
    TextView mDateText;
    @BindView(R.id.textview_details_group_name)
    TextView mGroupName;
    private SimpleDateFormat mDateFormatter;

    public static TaskDetailsFragment newInstance(){
        return new TaskDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        LinearLayout date = view.findViewById(R.id.linear_layout_details_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDateTimePicker();
            }
        });
        LinearLayout priority = view.findViewById(R.id.linear_layout_details_priority);
        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openChoosingPriority();
            }
        });

        LinearLayout group = view.findViewById(R.id.linear_layout_details_group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openChoosingGroup();
            }
        });

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
                if (mTitle.getText().toString().isEmpty()){
                    showError(getString(R.string.error_no_title));
                    return false;
                }
                mPresenter.saveTask(mTitle.getText().toString(), mNotes.getText().toString());
                getActivity().finish();
                return true;
            }
            case R.id.menu_fragment_details_remove:{
                mPresenter.removeTask();
                getActivity().finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showTask(final TaskItem item) {
        mTitle.setText(item.getTitle());
        mPriority.setText(item.getPriority().toString());
        mReminder.setChecked(item.isRemind());
        mDateFormatter = new SimpleDateFormat("EEEE, d MMMM y, kk:mm", Locale.getDefault());
        mDateText.setText(mDateFormatter.format(item.getDate()));
        mNotes.setText(item.getNotes());
        mGroupName.setText(Initializer.getGroupsRepo(getContext())
                .query(new GetGroupByIdLocalSpecification(item.getGroupId())).get(0).getName());
        mReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPresenter.setReminder(getContext(), b);
            }
        });
    }

    @Override
    public void showDateTimePicker(final Date defaultDate) {
        new SingleDateAndTimePickerDialog.Builder(getActivity())
                .title("Date and Time")
                .bottomSheet()
                .curved()
                .mainColor(getResources().getColor(android.R.color.black))
                .minutesStep(1)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {
                        picker.setDefaultDate(defaultDate);
                    }
                })
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        mPresenter.setDate(date);
                    }
                })
                .display();
    }

    @Override
    public void showChoosingPriority() {
        PriorityDialogFragment dialog = new PriorityDialogFragment();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Priority");
    }

    @Override
    public void showChoosingGroup() {
        GroupDialogFragment dialog = new GroupDialogFragment();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Group");
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mTitle, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setPresenter(TaskDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showPriority(Priority priority) {
        mPriority.setText(priority.toString());
    }

    @Override
    public void showDate(Date date) {
        mDateText.setText(mDateFormatter.format(date));
    }

    @Override
    public void showGroup(String name) {
        mGroupName.setText(name);
    }

    @Override
    public void onDialogItemClick(DialogFragment dialog, String which) {
        mPresenter.setPriority(Priority.valueOf(which));
        dialog.dismiss();
    }

    @Override
    public void onDialogItemClick(DialogFragment dialog, Group which) {
        mPresenter.setGroup(which);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
