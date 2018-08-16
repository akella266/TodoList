package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class GetTasksByGroupIdDbSpecification implements Specification<TaskItem, TaskItemDao> {

    private UUID groupId;
    private TaskItemDao mTaskDao;

    public GetTasksByGroupIdDbSpecification(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
    public List<TaskItem> getData() {
        return mTaskDao.getTasksByGroupId(groupId.toString());
    }

    @Override
    public void setDataSource(TaskItemDao dataSource) {
        this.mTaskDao = dataSource;
    }
}
