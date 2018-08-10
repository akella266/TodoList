package by.intervale.akella266.todolist.utils;

import by.intervale.akella266.todolist.data.models.TaskItem;

public interface OnPopupMenuItemTaskClickListener {
    void onEditClick(TaskItem item);
    void onCompleteClick(TaskItem item);
    void onDeleteClick(TaskItem item);
}
