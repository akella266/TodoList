package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.utils.InboxItem;

public class InboxAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<InboxItem> mList;
    private RecyclerView.RecycledViewPool viewPool;

    public InboxAdapter(Context context) {
        this.context = context;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InboxViewHolder inboxHolder = (InboxViewHolder)holder;
        inboxHolder.categoryName.setText(mList.get(position).getName());
        TasksAdapter adapter = new TasksAdapter(mList.get(position).getTaskItems());
        inboxHolder.listItems.setAdapter(adapter);
        inboxHolder.listItems.setLayoutManager(new LinearLayoutManager(context));
        adapter.notifyDataSetChanged();
        inboxHolder.listItems.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<InboxItem> mList) {
        this.mList = mList;
    }
}
