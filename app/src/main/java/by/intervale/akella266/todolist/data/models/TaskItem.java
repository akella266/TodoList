package by.intervale.akella266.todolist.data.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import by.intervale.akella266.todolist.utils.Priority;

public class TaskItem implements Comparable<TaskItem>, Serializable {

    private UUID id;
    private String title;
    private Date date;
    private String notes;
    private Priority priority;
    private boolean isComplete;
    private UUID groupId;

    public TaskItem(String title, Date date, String notes, Priority priority, UUID groupId, boolean isComplete) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.priority = priority;
        this.groupId = groupId;
        this.isComplete = isComplete;
    }

    public TaskItem(String title, Date date, String notes,UUID groupId, boolean isComplete) {
        this(title, date, notes,Priority.NONE, groupId, isComplete);
    }

    public TaskItem(String title, Date date, String notes,UUID groupId) {
        this(title, date, notes, Priority.NONE, groupId, false);
    }
    public TaskItem(){
        this("", Calendar.getInstance(Locale.getDefault()).getTime(), "", Priority.NONE, null, false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mName) {
        this.title = mName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date mDate) {
        this.date = mDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String mNotes) {
        this.notes = mNotes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority mPriority) {
        this.priority = mPriority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
    public int compareTo(@NonNull TaskItem taskItem) {
        return id == taskItem.getId() ? 0 : 1;
    }
}
