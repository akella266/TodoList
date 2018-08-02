package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.fragments.TaskDetailsFragment;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<TaskItem> mTasks;
    private boolean isEdit;

    public TasksAdapter(List<TaskItem> mTasks) {
        this.mTasks = mTasks;
        this.isEdit = false;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        final TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TaskDetailsActivity.newIntent(parent.getContext(),
                        mTasks.get(taskViewHolder.getAdapterPosition()));
                parent.getContext().startActivity(intent);
            }
        });
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem task = mTasks.get(position);
        holder.mTitle.setText(task.getTitle());
        holder.mNote.setText(task.getNotes());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        holder.mDate.setText(format.format(task.getDate()));
        if (isEdit){
            holder.mIcon.setVisibility(View.VISIBLE);
        }
        else{
            holder.mIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setTasks(List<TaskItem> mTasks) {
        this.mTasks = mTasks;
    }

    public List<TaskItem> getTasks() {
        return mTasks;
    }
}
