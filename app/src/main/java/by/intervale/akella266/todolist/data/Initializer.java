package by.intervale.akella266.todolist.data;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.repositories.json.GroupJsonRepository;
import by.intervale.akella266.todolist.data.repositories.json.TaskItemJsonRepository;
import by.intervale.akella266.todolist.data.repositories.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.repositories.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class Initializer {

    private static TaskItemLocalRepository sRepoTasks;
    private static Repository<TaskItem> sTasks;
    private static Repository<Group> sGroups;
    private static GroupLocalRepository sRepoGroups;

    public static void initialize(Context context){
        sGroups = new GroupJsonRepository(context);
        sTasks = new TaskItemJsonRepository(context);
        Group inbox = new Group("Inbox");
        inbox.setId(UUID.fromString("1-1-1-1-1"));
        sGroups.add(inbox);
        Group work = new Group("Work");
        sGroups.add(work);
        sGroups.add(new Group("Building"));
        sGroups.add(new Group("My"));
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

    public static Repository<TaskItem> getTasksRepo(Context context){
        if (sTasks == null) sTasks = new TaskItemJsonRepository(context);
        return sTasks;
    }

    public static Repository<Group> getGroupsRepo(Context context){
        if(sGroups == null) sGroups = new GroupJsonRepository(context);
        return sGroups;
    }

}
