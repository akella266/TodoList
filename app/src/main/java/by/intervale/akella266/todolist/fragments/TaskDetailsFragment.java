package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetTaskByIdSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.NotificationSheduler;
import by.intervale.akella266.todolist.utils.Priority;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskDetailsFragment extends Fragment
        implements PriorityDialogFragment.DialogPriorityListener,
        GroupDialogFragment.DialogGroupListener{

    public static final String ARGUMENT_DETAILS = "bundle.details";

    private Unbinder unbinder;
    private TaskItem mTask;
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
    private boolean isEdit;

    public static TaskDetailsFragment newInstance(UUID itemId){
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_DETAILS, itemId);
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        UUID itemId = null;
        if (getArguments() != null) itemId = (UUID) getArguments().getSerializable(ARGUMENT_DETAILS);

        if (itemId == null){
            isEdit = false;
            mTask = new TaskItem();
            mTask.setGroupId(UUID.fromString("1-1-1-1-1"));
        }
        else {
            mTask = new TaskItem(Initializer.getTasksLocal()
                    .query(new GetTaskByIdSpecification(itemId)).get(0));
            isEdit = true;
        }

        setFields();

        LinearLayout date = view.findViewById(R.id.linear_layout_details_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateTimePicker();
            }
        });
        LinearLayout priority = view.findViewById(R.id.linear_layout_details_priority);
        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChoosingPriority();
            }
        });

        LinearLayout group = view.findViewById(R.id.linear_layout_details_group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChoosingGroup();
            }
        });

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
                if ((title = mTitle.getText().toString()).isEmpty()){
                    Snackbar.make(mTitle, getString(R.string.error_no_title), Snackbar.LENGTH_SHORT)
                            .show();
                    return false;
                }
                mTask.setTitle(title.trim());
                mTask.setNotes(mNotes.getText().toString());
                if (isEdit) Initializer.getTasksLocal().update(mTask);
                else Initializer.getTasksLocal().add(mTask);
                getActivity().finish();
                return true;
            }
            case R.id.menu_fragment_details_remove:{
                Initializer.getTasksLocal().remove(mTask);
                getActivity().finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogItemClick(DialogFragment dialog, String which) {
        mTask.setPriority(Priority.valueOf(which));
        mPriority.setText(which);
        dialog.dismiss();
    }

    @Override
    public void onDialogItemClick(DialogFragment dialog, Group which) {
        mTask.setGroupId(which.getId());
        mGroupName.setText(which.getName());
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    private void setFields(){
        mTitle.setText(mTask.getTitle());
        mPriority.setText(mTask.getPriority().toString());
        mReminder.setChecked(mTask.isRemind());
        mDateFormatter = new SimpleDateFormat("EEEE, d MMMM y, kk:mm", Locale.getDefault());
        mDateText.setText(mDateFormatter.format(mTask.getDate()));
        mNotes.setText(mTask.getNotes());
        mGroupName.setText(Initializer.getGroupsLocal()
                .query(new GetGroupNameByIdSpecification(mTask.getGroupId())).get(0).getName());

        mReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mTask.setRemind(true);
                    NotificationSheduler.setReminder(getContext(), mTask);
                }
                else {
                    mTask.setRemind(false);
                    NotificationSheduler.cancelReminder(getContext(), mTask);
                }
            }
        });
    }

    private void openDateTimePicker(){
        new SingleDateAndTimePickerDialog.Builder(getActivity())
                .title("Date and Time")
                .bottomSheet()
                .curved()
                .mainColor(getResources().getColor(android.R.color.black))
                .minutesStep(1)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {
                        picker.setDefaultDate(mTask.getDate());
                    }
                })
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        Log.i("fragmentadd picker", "onDateSelected");
                        mTask.setDate(date);
                        mDateText.setText(mDateFormatter.format(date));
                    }
                })
                .display();
    }

    private void openChoosingPriority(){
        PriorityDialogFragment dialog = new PriorityDialogFragment();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Priority");
    }

    private void openChoosingGroup(){
        GroupDialogFragment dialog = new GroupDialogFragment();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Group");
    }
}
