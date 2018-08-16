package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class GetTasksByNameDbSpecification implements Specification<TaskItem, TaskItemDao> {

    private String name;
    private TaskItemDao mTaskDao;

    public GetTasksByNameDbSpecification(String name) {
        this.name = name;
    }

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> items = mTaskDao.getTasksByName(name);
        return items;
    }

    @Override
    public void setDataSource(TaskItemDao dataSource) {
        this.mTaskDao = dataSource;
    }
}
