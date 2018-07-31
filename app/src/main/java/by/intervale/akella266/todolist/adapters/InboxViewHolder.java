package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import by.intervale.akella266.todolist.R;

public class InboxViewHolder extends RecyclerView.ViewHolder {

    TextView categoryName;
    RecyclerView listItems;

    public InboxViewHolder(View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.inbox_name_category);
        listItems = itemView.findViewById(R.id.inbox_inner_recycler);
    }
}
