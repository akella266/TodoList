package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.utils.InboxItem;
import by.intervale.akella266.todolist.utils.ItemTouchActions;
import by.intervale.akella266.todolist.utils.ItemTouchCallback;

public class CommonAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<InboxItem> mList;
    private List<ItemTouchHelper> mHelpers;
    private RecyclerView.RecycledViewPool viewPool;
    private ItemTouchActions itemTouchActions;
    private int countButtons;
    private boolean isEdit;

    public CommonAdapter(Context context, int countButtons, ItemTouchActions itemTouchActions) {
        this.context = context;
        this.itemTouchActions = itemTouchActions;
        this.countButtons = countButtons;
        viewPool = new RecyclerView.RecycledViewPool();
        mHelpers = new ArrayList<>();
        isEdit = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommonViewHolder inboxHolder = (CommonViewHolder)holder;
        inboxHolder.categoryName.setText(mList.get(position).getName());
        if(inboxHolder.categoryName.getText().length() == 0){
            inboxHolder.categoryName.setVisibility(View.GONE);
        }
        TasksAdapter adapter = new TasksAdapter(mList.get(position).getTaskItems());
        inboxHolder.listItems.setAdapter(adapter);
        inboxHolder.listItems.setLayoutManager(new LinearLayoutManager(context));
        inboxHolder.listItems.setRecycledViewPool(viewPool);
        adapter.setEdit(isEdit);
        if (isEdit){
            attachHelper(inboxHolder);
        }
        else{
            detachHelper();
        }
        adapter.notifyDataSetChanged();
    }

    private void attachHelper(final CommonViewHolder holder) {
        ItemTouchHelper itemTouchhelper;
        if(holder.categoryName.getText().equals(context.getString(R.string.completed_tasks)))
                itemTouchhelper = new ItemTouchHelper(new ItemTouchCallback(context,
                                                                    1,
                                                                    itemTouchActions));
        else{
            itemTouchhelper = new ItemTouchHelper(new ItemTouchCallback(context,
                    countButtons,
                    itemTouchActions));
        }
        itemTouchhelper.attachToRecyclerView(holder.listItems);
        mHelpers.add(itemTouchhelper);
    }

    private void detachHelper(){
        for(ItemTouchHelper helper: mHelpers){
            helper.attachToRecyclerView(null);
        }
        mHelpers.clear();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<InboxItem> mList) {
        this.mList = mList;
    }
}
