package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class GetTaskByIdDbSpecification implements Specification<TaskItem, TaskItemDao> {

    private UUID itemId;
    private TaskItemDao mTaskDao;

    public GetTaskByIdDbSpecification(UUID itemId) {
        this.itemId = itemId;
    }

    @Override
    public List<TaskItem> getData() {
        mTaskDao.getTaskById(itemId.toString());
        List<TaskItem> tasks = new ArrayList<>();
        TaskItem item = null;
        if ((item = mTaskDao.getTaskById(itemId.toString())) != null){
            tasks.add(item);
            return tasks;
        }
        return null;
    }

    @Override
    public void setDataSource(TaskItemDao dataSource) {
        this.mTaskDao = dataSource;
    }
}
