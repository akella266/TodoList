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
import by.intervale.akella266.todolist.utils.OnToolbarButtonsClickListener;
import by.intervale.akella266.todolist.utils.Priority;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class TaskDetailsFragment extends Fragment
        implements DialogFragmentPriority.DialogPriorityListener,
        DialogFragmentGroup.DialogGroupListener,
        OnToolbarButtonsClickListener{

    public static final String ARGS_DETAILS = "bundle.details";

    private TaskItem mTask;
    private EditText mTitle;
    private Switch mReminder;
    private TextView mPriority;
    private EditText mNotes;
    private TextView mDateText;
    private SimpleDateFormat mDateFormater;
    private TextView mGroupName;
    private boolean isEdit;

    public static TaskDetailsFragment getInstance(TaskItem item){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DETAILS, item);
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        if (getArguments() != null)
            mTask = (TaskItem) getArguments().getSerializable(ARGS_DETAILS);

        if (mTask == null){
            isEdit = false;
            mTask = new TaskItem();
            mTask.setGroupId(UUID.fromString("1-1-1-1-1"));
        }
        else isEdit = true;

        setFields(view);

        LinearLayout date = view.findViewById(R.id.details_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateTimePicker();
            }
        });
        LinearLayout priority = view.findViewById(R.id.details_priority);
        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChoosingPriority();
            }
        });

        LinearLayout group = view.findViewById(R.id.details_group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChoosingGroup();
            }
        });

        return view;
    }

    private void setFields(View view){
        mDateText = view.findViewById(R.id.details_date_text);
        mTitle =  view.findViewById(R.id.details_title);
        mReminder =  view.findViewById(R.id.details_remind);
        mNotes = view.findViewById(R.id.details_notes);
        mPriority =  view.findViewById(R.id.details_priority_text);
        mGroupName = view.findViewById(R.id.details_group_name);

        mTitle.setText(mTask.getTitle());
        mPriority.setText(mTask.getPriority().toString());
        mDateFormater = new SimpleDateFormat("EEEE, d MMMM y, k:m", Locale.getDefault());
        mDateText.setText(mDateFormater.format(mTask.getDate()));
        mNotes.setText(mTask.getNotes());
        mGroupName.setText(Initializer.getGroupsLocal()
                .query(new GetGroupNameByIdSpecification(mTask.getGroupId())).get(0).getName());
    }

    private void openDateTimePicker(){
        new SingleDateAndTimePickerDialog.Builder(getActivity())
                .title("Date and Time")
                .bottomSheet()
                .curved()
                .mainColor(getResources().getColor(R.color.colorPrimary))
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
                        mDateText.setText(mDateFormater.format(date));
                    }
                })
                .display();
    }

    private void openChoosingPriority(){
        DialogFragmentPriority dialog = new DialogFragmentPriority();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Priority");
    }

    private void openChoosingGroup(){
        DialogFragmentGroup dialog = new DialogFragmentGroup();
        dialog.setListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            dialog.show(activity.getSupportFragmentManager(), "Group");
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
}
