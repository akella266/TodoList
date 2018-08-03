package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.intervale.akella266.todolist.GroupDetailsActivity;
import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private Context mContext;
    private List<Group> mList;

    public GroupAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        final GroupViewHolder groupViewHolder = new GroupViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GroupDetailsActivity.getStartIntent(mContext,
                        mList.get(groupViewHolder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = mList.get(position);
        holder.mNameGroup.setText(group.getName());
        holder.mCountTasks.setText(mContext.getString(R.string.item_group_count_tasks, group.getCountTasks()+""));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Group> list) {
        this.mList = list;
    }

    public List<Group> getList() {
        return mList;
    }
}
