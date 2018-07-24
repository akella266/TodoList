package by.intervale.akella266.todolist.utils;

import java.util.Date;

public class TaskItem {

    private String mName;
    private Date mDate;
    private String mNotes;

    public TaskItem(String mName, Date mDate, String mNotes) {
        this.mName = mName;
        this.mDate = mDate;
        this.mNotes = mNotes;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }
}
