package by.intervale.akella266.todolist.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import by.intervale.akella266.todolist.data.models.TaskItem;

public class TasksDiffUtilsCallback extends DiffUtil.Callback {

    private List<TaskItem> mOldList;
    private List<TaskItem> mNewList;

    public TasksDiffUtilsCallback(List<TaskItem> mOldList, List<TaskItem> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        return mOldList != null ? mOldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewList != null ? mNewList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId().equals(mNewList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
