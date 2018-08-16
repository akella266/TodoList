package by.intervale.akella266.todolist.data;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.repositories.db.database.Database;
import by.intervale.akella266.todolist.data.repositories.db.GroupDbRepository;
import by.intervale.akella266.todolist.data.repositories.db.TaskItemDbRepository;
import by.intervale.akella266.todolist.data.repositories.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.repositories.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.specifications.db.group.IncreaseCountTasksDbSpecification;

public class Initializer {

    private static Database db;
    private static Repository<TaskItem> sTasks;
    private static Repository<Group> sGroups;

    public static void initialize(Context context){
        sGroups = getGroupsRepo(context);
        sTasks = getTasksRepo(context);
        Group inbox = new Group("Inbox");
        inbox.setId(UUID.fromString("1-1-1-1-1"));
        sGroups.add(inbox);
        Group work = new Group("Work");
        sGroups.add(work);
        sGroups.add(new Group("Building"));
        sGroups.add(new Group("My"));
        sTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(inbox.getIdUUID()));
        sTasks.add(new TaskItem("This is task withour reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(inbox.getIdUUID()));
        sTasks.add(new TaskItem("This is task withouy reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(inbox.getIdUUID()));
        sTasks.add(new TaskItem("This is task withouj reminder",
                Calendar.getInstance().getTime(), "This is note", inbox.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(inbox.getIdUUID()));
        sTasks.add(new TaskItem("This is task withouj reminder",
                new Date(Calendar.getInstance().getTime().getTime()+100000000L), "This is note", inbox.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(inbox.getIdUUID()));
        sTasks.add(new TaskItem("This is task withour reminder",
                new Date(Calendar.getInstance().getTime().getTime()+100000000L), "This is note", work.getIdUUID()));
        sGroups.query(new IncreaseCountTasksDbSpecification(work.getIdUUID()));
        sTasks.add(new TaskItem("This is task without reminder",
                Calendar.getInstance().getTime(), "This is note", work.getIdUUID(),true));
        sGroups.query(new IncreaseCountTasksDbSpecification(work.getIdUUID()));
    }

    public static Repository<TaskItem> getTasksRepo(Context context){
        if (sTasks == null) {
            if (db == null)
                db  = Database.getAppDatabase(context);
            sTasks = new TaskItemDbRepository(db.taskItemDao());
        }
        return sTasks;
    }

    public static Repository<Group> getGroupsRepo(Context context){
        if(sGroups == null) {
            if (db == null)
                db  = Database.getAppDatabase(context);
            sGroups = new GroupDbRepository(db.groupDao());
        }
        return sGroups;
    }

}
