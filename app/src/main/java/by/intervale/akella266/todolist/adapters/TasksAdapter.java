package by.intervale.akella266.todolist.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.utils.TaskItem;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<TaskItem> mTasks;

    public TasksAdapter(List<TaskItem> mTasks) {
        this.mTasks = mTasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem task = mTasks.get(position);
        holder.mTitle.setText(task.getTitle());
        holder.mNote.setText(task.getNotes());
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        holder.mDate.setText(format.format(task.getDate()));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void setTasks(List<TaskItem> mTasks) {
        this.mTasks = mTasks;
    }
}
