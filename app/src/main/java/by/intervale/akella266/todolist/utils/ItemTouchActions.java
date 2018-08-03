package by.intervale.akella266.todolist.utils;

import android.support.v7.widget.RecyclerView;

public interface ItemTouchActions {
    void onLeftClick(RecyclerView.Adapter adapter, int position);
    void onRightClick(RecyclerView.Adapter adapter, int position);
}
