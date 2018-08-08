package by.intervale.akella266.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.todolist.R;

public class CommonViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textview_inbox_name_category)
    TextView categoryName;
    @BindView(R.id.recycler_inbox)
    RecyclerView listItems;

    public CommonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
