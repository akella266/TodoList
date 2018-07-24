package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import by.intervale.akella266.todolist.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView mTitle;
    TextView mNote;
    TextView mDate;

    public TaskViewHolder(View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.task_title);
        mNote = itemView.findViewById(R.id.task_note);
        mDate = itemView.findViewById(R.id.task_date);
    }
}
