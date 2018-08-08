package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textview_task_title)
    TextView mTitle;
    @BindView(R.id.textview_task_note)
    TextView mNote;
    @BindView(R.id.textview_task_date)
    TextView mDate;
    @BindView(R.id.image_more)
    ImageView mMore;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
