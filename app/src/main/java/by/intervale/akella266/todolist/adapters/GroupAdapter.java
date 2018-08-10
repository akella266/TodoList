package by.intervale.akella266.todolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.views.todo.OnGroupItemClickListener;
import by.intervale.akella266.todolist.utils.OnPopupMenuItemGroupClickListener;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder>{

    private Context mContext;
    private List<Group> mList;
    private OnGroupItemClickListener mItemClickListener;
    private OnPopupMenuItemGroupClickListener mPopupClickListener;

    public GroupAdapter(Context context,OnGroupItemClickListener itemClickListener,
                        OnPopupMenuItemGroupClickListener listener) {
        this.mContext = context;
        this.mItemClickListener = itemClickListener;
        this.mPopupClickListener = listener;
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
                mItemClickListener.onGroupItemClick(mList.get(groupViewHolder.getAdapterPosition()));
            }
        });
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupViewHolder holder, int position) {
        final Group group = mList.get(position);
        holder.mNameGroup.setText(group.getName());
        holder.mCountTasks.setText(mContext.getString(R.string.item_group_count_tasks, group.getCountTasks()+""));
        holder.mImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, group);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void showPopupMenu(View view,final Group group){
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.item_completed_or_group, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_edit:{
                        mPopupClickListener.onEditClick(group);
                        return true;
                    }
                    case R.id.item_delete:{
                        mPopupClickListener.onDeleteClick(group);
                        return true;
                    }
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    public void setList(List<Group> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<Group> getList() {
        return mList;
    }

}
