package by.intervale.akella266.todolist.data.repositories.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.intervale.akella266.todolist.data.models.TaskItem;

@Dao
public interface TaskItemDao {

    @Insert
    void insert(final TaskItem item);

    @Update
    void update(final TaskItem item);

    @Delete
    void delete(final TaskItem item);

    @Query("select * from tasks where isComplete = :isCompleted")
    List<TaskItem> getTasksItems(final boolean isCompleted);

    @Query("select * from tasks where id = :id")
    TaskItem getTaskById(final String id);

    @Query("select * from tasks where groupId = :id")
    List<TaskItem> getTasksByGroupId(String id);

    @Query("select * from tasks where title LIKE :name")
    List<TaskItem> getTasksByName(String name);
}
