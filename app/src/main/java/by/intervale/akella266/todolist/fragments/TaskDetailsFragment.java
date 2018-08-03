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

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.NotificationSheduler;
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;
import by.intervale.akella266.todolist.utils.Priority;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskDetailsFragment extends Fragment
        implements PriorityDialogFragment.DialogPriorityListener,
        GroupDialogFragment.DialogGroupListener,
        OnToolbarButtonsClickListener{

    public static final String ARGUMENT_DETAILS = "bundle.details";

    private TaskItem mTask;
    private EditText mTitle;
    private Switch mReminder;
    private TextView mPriority;
    private EditText mNotes;
    private TextView mDateText;
    private SimpleDateFormat mDateFormatter;
    private TextView mGroupName;
    private boolean isEdit;

    public static TaskDetailsFragment newInstance(TaskItem item){
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_DETAILS, item);
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        if (getArguments() != null) mTask = (TaskItem) getArguments().getSerializable(ARGUMENT_DETAILS);

        if (mTask == null){
            isEdit = false;
            mTask = new TaskItem();
            mTask.setGroupId(UUID.fromString("1-1-1-1-1"));
        }
        else isEdit = true;

        setFields(view);

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


    @Override
    public void onLeftButtonClick(View view) {
        getActivity().finish();
    }

    @Override
    public void onRightButtonClick(View view) {
        String title;
        if ((title = mTitle.getText().toString()).isEmpty()){
            Snackbar.make(mTitle, getString(R.string.error_no_title), Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        mTask.setTitle(title.trim());
        mTask.setNotes(mNotes.getText().toString());
        if (isEdit) Initializer.getTasksLocal().update(mTask);
        else Initializer.getTasksLocal().add(mTask);
        getActivity().finish();
    }

    private void setFields(View view){
        mDateText = view.findViewById(R.id.textview_details_date);
        mTitle =  view.findViewById(R.id.edittext_details_title);
        mReminder =  view.findViewById(R.id.switch_details_remind);
        mNotes = view.findViewById(R.id.edittext_details_notes);
        mPriority =  view.findViewById(R.id.textview_details_priority);
        mGroupName = view.findViewById(R.id.textview_details_group_name);

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
