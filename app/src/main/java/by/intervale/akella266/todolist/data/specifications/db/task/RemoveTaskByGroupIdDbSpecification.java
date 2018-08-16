package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.repositories.db.dao.TaskItemDao;

public class RemoveTaskByGroupIdDbSpecification implements Specification<TaskItem, TaskItemDao> {

    private UUID mGroupId;
    private TaskItemDao mTaskDao;

    public RemoveTaskByGroupIdDbSpecification(UUID mGroupId) {
        this.mGroupId = mGroupId;
    }

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> items = mTaskDao.getTasksByGroupId(mGroupId.toString());
        for(TaskItem item : items){
            mTaskDao.delete(item);
        }
        return null;
    }

    @Override
    public void setDataSource(TaskItemDao dataSource) {
        this.mTaskDao = dataSource;
    }
}
