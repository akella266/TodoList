package by.intervale.akella266.todolist.utils;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TaskItem implements Comparable<TaskItem>, Serializable {

    private UUID id;
    private String title;
    private Date date;
    private String notes;
    private Priority priority;
    private boolean isComplete;

    public TaskItem(UUID id, String title, Date date, String notes, Priority priority, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.priority = priority;
        this.isComplete = isComplete;
    }

    public TaskItem(UUID id, String title, Date date, String notes,boolean isComplete) {
        this(id, title, date, notes,Priority.NONE, isComplete);
    }

    public TaskItem(UUID id, String title, Date date, String notes) {
        this(id, title, date, notes,Priority.NONE, false);
    }
    public TaskItem(){
        this(UUID.randomUUID(), "", Calendar.getInstance(Locale.getDefault()).getTime(), "", Priority.NONE, false);
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

    @Override
    public int compareTo(@NonNull TaskItem taskItem) {
        return id == taskItem.getId() ? 0 : 1;
    }
}
