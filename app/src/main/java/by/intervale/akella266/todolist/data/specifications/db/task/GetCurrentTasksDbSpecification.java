package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class GetCurrentTasksDbSpecification implements Specification<TaskItem, TaskItemDao> {

    private TaskItemDao mTaskDao;

    @Override
    public List<TaskItem> getData() {
        return mTaskDao.getTasksItems(false);
    }

    @Override
    public void setDataSource(TaskItemDao dataSource) {
        this.mTaskDao = dataSource;
    }
}
