package by.intervale.akella266.todolist.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import by.intervale.akella266.todolist.data.repositories.db.converters.DateConverter;
import by.intervale.akella266.todolist.data.repositories.db.converters.PriorityConverter;
import by.intervale.akella266.todolist.utils.Priority;


@Entity(tableName = "tasks")
public class TaskItem  implements Comparable<TaskItem>, Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    @TypeConverters(DateConverter.class)
    private Date date;
    private String notes;
    @TypeConverters(PriorityConverter.class)
    private Priority priority;
    private boolean isComplete;
    private boolean isRemind;
    private String groupId;

    public TaskItem(UUID id, String title, Date date, String notes, Priority priority, UUID groupId, boolean isRemind, boolean isComplete) {
        this.id = id.toString();
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.priority = priority;
        this.groupId = groupId == null ? null : groupId.toString();
        this.isRemind = isRemind;
        this.isComplete = isComplete;
    }

    public TaskItem(String title, Date date, String notes, Priority priority, UUID groupId, boolean isRemind, boolean isComplete){
        this(UUID.randomUUID(), title, date, notes, priority, groupId, isRemind, isComplete);
    }
    public TaskItem(String title, Date date, String notes,UUID groupId, boolean isComplete) {
        this(title, date, notes,Priority.NONE, groupId, false, isComplete);
    }

    public TaskItem(String title, Date date, String notes,UUID groupId) {
        this(title, date, notes, Priority.NONE, groupId,false, false);
    }
    public TaskItem(){
        this("", Calendar.getInstance(Locale.getDefault()).getTime(), "", Priority.NONE, null,false, false);
    }

    public TaskItem(TaskItem another){
        this(another.getIdUUID(), another.getTitle(),another.getDate(), another.getNotes(), another.getPriority(),
                another.getGroupIdUUID(), another.isRemind(), another.isComplete());
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

    public UUID getIdUUID() {
        return UUID.fromString(id);
    }

    public String getId() {return id;}

    public void setId(UUID id) {
        this.id = id.toString();
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

    public UUID getGroupIdUUID() {
        return UUID.fromString(groupId);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId.toString();
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if(!(obj instanceof TaskItem)) return false;
        else return UUID.fromString(id).compareTo(((TaskItem) obj).getIdUUID()) == 0;
    }

    @Override
    public int compareTo(@NonNull TaskItem taskItem) {
        return UUID.fromString(id).compareTo(taskItem.getIdUUID());
    }
}
