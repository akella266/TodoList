package by.intervale.akella266.todolist.data.models;

import android.support.annotation.NonNull;

import java.util.List;

public class InboxItem implements Comparable<InboxItem>{

    private String name;
    private List<TaskItem> taskItems;

    public InboxItem(String name, List<TaskItem> taskItems) {
        this.name = name;
        this.taskItems = taskItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskItem> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }

    @Override
    public int compareTo(@NonNull InboxItem inboxItem) {
        return name.compareTo(inboxItem.getName());
    }
}
