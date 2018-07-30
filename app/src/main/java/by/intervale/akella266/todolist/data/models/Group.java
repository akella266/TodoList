package by.intervale.akella266.todolist.data.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

public class Group implements Comparable<Group>, Serializable {

    private UUID id;
    private String name;
    private int countTasks;

    public Group() {
    }

    public Group(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.countTasks = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountTasks() {
        return countTasks;
    }

    public void addTask() {
        this.countTasks++;
    }

    @Override
    public int compareTo(@NonNull Group group) {
        return id == group.getId() ? 0 : 1;
    }
}
