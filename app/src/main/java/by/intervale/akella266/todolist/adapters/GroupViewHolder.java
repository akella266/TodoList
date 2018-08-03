package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import by.intervale.akella266.todolist.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    TextView mNameGroup;
    TextView mCountTasks;

    public GroupViewHolder(View itemView) {
        super(itemView);

        mNameGroup = itemView.findViewById(R.id.textview_item_group_name);
        mCountTasks = itemView.findViewById(R.id.textview_item_group_count_tasks);
    }


}
