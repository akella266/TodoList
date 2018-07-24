package by.intervale.akella266.todolist.data;

import java.util.Calendar;

import by.intervale.akella266.todolist.data.local.LocalRepo;
import by.intervale.akella266.todolist.utils.TaskItem;

public class Initializer {

    public static LocalRepo getTasksLocal(){
        LocalRepo repo = new LocalRepo();
        repo.add(new TaskItem(1, "This is task withour reminder",
                                    Calendar.getInstance().getTime(), "This is note"));
        repo.add(new TaskItem(2, "This is task withour reminder",
                                    Calendar.getInstance().getTime(), "This is note"));
        repo.add(new TaskItem(3, "This is task withour reminder",
                                    Calendar.getInstance().getTime(), "This is note"));
        repo.addCompleted(new TaskItem(4, "This is task withour reminder",
                                    Calendar.getInstance().getTime(), "This is note"));
        repo.addCompleted(new TaskItem(5, "This is task withour reminder",
                                    Calendar.getInstance().getTime(), "This is note"));
        return repo;
    }
}
