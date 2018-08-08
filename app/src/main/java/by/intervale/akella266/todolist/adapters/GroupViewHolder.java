package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textview_item_group_name)
    TextView mNameGroup;
    @BindView(R.id.textview_item_group_count_tasks)
    TextView mCountTasks;

    public GroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
