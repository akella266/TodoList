package by.intervale.akella266.todolist.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "groups")
public class Group implements Comparable<Group>, Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private int countTasks;

    public Group() {
        this("");
    }

    public Group(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.countTasks = 0;
    }

    public UUID getIdUUID() {
        return UUID.fromString(id);
    }

    public String getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountTasks() {
        return countTasks;
    }

    public void setId(UUID id) {
        this.id = id.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCountTasks(int countTasks) {
        this.countTasks = countTasks;
    }

    public void increaseCountTasks() {
        this.countTasks++;
    }

    public void decreaseCountTasks(){this.countTasks--;}

    @Override
    public int compareTo(@NonNull Group group) {
        return UUID.fromString(id) == group.getIdUUID() ? 0 : 1;
    }
}
