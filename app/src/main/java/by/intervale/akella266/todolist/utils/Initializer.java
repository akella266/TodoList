package by.intervale.akella266.todolist.utils;

import java.util.Calendar;
import java.util.UUID;

import by.intervale.akella266.todolist.data.local.LocalRepo;
import by.intervale.akella266.todolist.utils.TaskItem;

public class Initializer {

    private static LocalRepo repo;

    public static LocalRepo getTasksLocal(){
        if (repo == null) {
            repo = new LocalRepo();
            repo.add(new TaskItem(UUID.randomUUID(), "This is task withour reminder",
                    Calendar.getInstance().getTime(), "This is note"));
            repo.add(new TaskItem(UUID.randomUUID(), "This is task withour reminder",
                    Calendar.getInstance().getTime(), "This is note"));
            repo.add(new TaskItem(UUID.randomUUID(), "This is task withour reminder",
                    Calendar.getInstance().getTime(), "This is note"));
            repo.add(new TaskItem(UUID.randomUUID(), "This is task withour reminder",
                    Calendar.getInstance().getTime(), "This is note", true));
            repo.add(new TaskItem(UUID.randomUUID(), "This is task withour reminder",
                    Calendar.getInstance().getTime(), "This is note", true));
        }
        return repo;
    }
}
