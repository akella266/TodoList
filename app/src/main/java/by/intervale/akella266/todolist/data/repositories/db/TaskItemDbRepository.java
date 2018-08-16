package by.intervale.akella266.todolist.data.repositories.db;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class TaskItemDbRepository implements Repository<TaskItem> {

    private TaskItemDao mTaskDao;

    public TaskItemDbRepository(TaskItemDao mTaskDao) {
        this.mTaskDao = mTaskDao;
    }

    @Override
    public void add(TaskItem item) {
        mTaskDao.insert(item);
    }

    @Override
    public void update(TaskItem item) {
        mTaskDao.update(item);
    }

    @Override
    public void remove(TaskItem item) {
        mTaskDao.delete(item);
    }

    @Override
    public List<TaskItem> query(Specification specification) {
        specification.setDataSource(mTaskDao);
        return specification.getData();
    }
}
