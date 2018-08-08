package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.TaskDetailsActivity;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;
import by.intervale.akella266.todolist.utils.ItemTouchAdapter;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemClickListener;
import by.intervale.akella266.todolist.utils.TasksDiffUtilsCallback;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder>
        implements ItemTouchAdapter{

    private Context context;
    private List<TaskItem> mTasks;
    private OnPopupMenuItemClickListener mClickListener;

    public TasksAdapter(Context context,
                        List<TaskItem> mTasks,
                        OnPopupMenuItemClickListener listener) {
        this.context = context;
        this.mTasks = mTasks;
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        final TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TaskDetailsActivity.getStartIntent(parent.getContext(),
                        mTasks.get(taskViewHolder.getAdapterPosition()).getId());
                parent.getContext().startActivity(intent);
            }
        });
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {
        final TaskItem task = mTasks.get(position);
        holder.mTitle.setText(task.getTitle());
        holder.mNote.setText(task.getNotes());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        holder.mDate.setText(format.format(task.getDate()));
        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    private void showPopupMenu(View view,final TaskItem task){
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (task.isComplete())
            popupMenu.getMenuInflater().inflate(R.menu.item_completed, popupMenu.getMenu());
        else{
            popupMenu.getMenuInflater().inflate(R.menu.item_active, popupMenu.getMenu());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_edit:{
                        Intent intent = TaskDetailsActivity.getStartIntent(context, task.getId());
                        context.startActivity(intent);
                        return true;
                    }
                    case R.id.item_complete:{
                        mClickListener.onItemClick(task);
                        return true;
                    }
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    public void update(List<TaskItem> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new TasksDiffUtilsCallback(mTasks, newList));

        mTasks.clear();
        mTasks.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void setTasks(List<TaskItem> mTasks) {
        this.mTasks = mTasks;
    }

    public List<TaskItem> getTasks() {
        return mTasks;
    }

    @Override
    public void onItemDismiss(int position) {
        Initializer.getTasksLocal().remove(mTasks.get(position));
        mTasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, mTasks.size());
    }
}
