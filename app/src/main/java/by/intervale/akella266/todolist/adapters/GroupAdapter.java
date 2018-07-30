package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private Context context;
    private List<Group> list;

    public GroupAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = list.get(position);
        holder.mNameGroup.setText(group.getName());
        holder.mCountTasks.setText(context.getString(R.string.item_group_count_tasks, group.getCountTasks()+""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Group> list) {
        this.list = list;
    }
}
