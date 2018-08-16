package by.intervale.akella266.todolist.data.repositories.db.database;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.GroupDao;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

@android.arch.persistence.room.Database(entities = {TaskItem.class, Group.class}, version = 1)
public abstract class Database  extends RoomDatabase{

    private static Database INSTANCE;

    public abstract TaskItemDao taskItemDao();
    public abstract GroupDao groupDao();

    public static Database getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "user-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
