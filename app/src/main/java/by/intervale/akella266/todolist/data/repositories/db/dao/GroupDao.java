package by.intervale.akella266.todolist.data.repositories.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import by.intervale.akella266.todolist.data.models.Group;

@Dao
public interface GroupDao {

    @Insert
    void insert(final Group item);

    @Update
    void update(final Group item);

    @Delete
    void delete(final Group item);

    @Query("select * from groups where id = :id")
    Group getGroupById(final String id);

    @Query("select * from groups")
    List<Group> getGroups();
}
