package by.intervale.akella266.todolist.data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class Initializer {

    private static TaskItemLocalRepository sRepoTasks;
    private static Repository<TaskItem> sTasks;
    private static GroupLocalRepository sRepoGroups;

    public static void initialize(){
        sRepoGroups = new GroupLocalRepository();
        Group inbox = new Group("Inbox");
        inbox.setId(UUID.fromString("1-1-1-1-1"));
        sRepoGroups.add(inbox);
        Group work = new Group("Work");
        sRepoGroups.add(work);
        sRepoGroups.add(new Group("Building"));
        sRepoGroups.add(new Group("My"));
        sTasks = new TaskItemLocalRepository();
        sTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        sTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        sTasks.add(new TaskItem("This is task withouy reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        sTasks.add(new TaskItem("This is task withouj reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getId()));
        sTasks.add(new TaskItem("This is task withouj reminder",
                new Date(Calendar.getInstance().getTime().getTime()+100000000L), "This is note", inbox.getId()));
        sTasks.add(new TaskItem("This is task withour reminder",
                new Date(Calendar.getInstance().getTime().getTime()+100000000L), "This is note", work.getId()));
        sTasks.add(new TaskItem("This is task without reminder",
                Calendar.getInstance().getTime(), "This is note", work.getId(),true));
    }

    public static TaskItemLocalRepository getTasksLocal(){
        if (sRepoTasks == null) initialize();
        return sRepoTasks;
    }

    public static Repository<TaskItem> getTasksRepo(){
        if (sTasks == null) initialize();
        return sTasks;
    }

    public static GroupLocalRepository getGroupsLocal(){
        if(sRepoGroups == null) initialize();
        return sRepoGroups;
    }
}
