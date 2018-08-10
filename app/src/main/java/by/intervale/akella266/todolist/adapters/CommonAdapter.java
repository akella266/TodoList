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
import by.intervale.akella266.todolist.data.models.InboxItem;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemTaskClickListener;

public class CommonAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<InboxItem> mList;
    private RecyclerView.RecycledViewPool mViewPool;
    private OnPopupMenuItemTaskClickListener mListener;

    public CommonAdapter(Context context, OnPopupMenuItemTaskClickListener listener){
        this.context = context;
        mViewPool = new RecyclerView.RecycledViewPool();
        this.mListener = listener;
    }

    public CommonAdapter(Context context) {
        this(context, null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inbox, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommonViewHolder inboxHolder = (CommonViewHolder)holder;
        inboxHolder.categoryName.setText(mList.get(position).getName());
        if(inboxHolder.categoryName.getText().length() == 0) inboxHolder.categoryName
                .setVisibility(View.GONE);
        final TasksAdapter adapter = new TasksAdapter(context,
                mList.get(position).getTaskItems(), mListener);
        inboxHolder.listItems.setAdapter(adapter);
        inboxHolder.listItems.setLayoutManager(new LinearLayoutManager(context));
        inboxHolder.listItems.setRecycledViewPool(mViewPool);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<InboxItem> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
