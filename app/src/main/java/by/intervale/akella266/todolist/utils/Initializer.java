package by.intervale.akella266.todolist.utils;

import java.util.Calendar;
import java.util.UUID;

import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class Initializer {

    private static TaskItemLocalRepository repoTasks;
    private static GroupLocalRepository repoGroups;

    public static void initialize(){
        repoGroups = new GroupLocalRepository();
        Group inbox = new Group("Inbox");
        repoGroups.add(inbox);
        Group work = new Group("Work");
        repoGroups.add(work);
        repoGroups.add(new Group("Building"));
        repoGroups.add(new Group("My"));
        repoTasks = new TaskItemLocalRepository();
        repoTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        inbox.addTask();
        repoTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        inbox.addTask();
        repoTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        inbox.addTask();
        repoTasks.add(new TaskItem("This is task without reminder",
                Calendar.getInstance().getTime(), "This is note", work.getId(),true));
        work.addTask();
    }

    public static TaskItemLocalRepository getTasksLocal(){
        if (repoTasks == null) {
            initialize();
        }
        return repoTasks;
    }

    public static GroupLocalRepository getGroupsLocal(){
        if(repoGroups == null){
            initialize();
        }
        return repoGroups;
    }
}
