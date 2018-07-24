package by.intervale.akella266.todolist.utils;

import android.support.annotation.NonNull;

import java.util.Date;

public class TaskItem implements Comparable<TaskItem> {

    private long mId;
    private String mTitle;
    private Date mDate;
    private String mNotes;

    public TaskItem(long mId, String mName, Date mDate, String mNotes) {
        this.mId = mId;
        this.mTitle = mName;
        this.mDate = mDate;
        this.mNotes = mNotes;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mName) {
        this.mTitle = mName;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    @Override
    public int compareTo(@NonNull TaskItem taskItem) {
        return mId == taskItem.getmId() ? 0 : 1;
    }
}
