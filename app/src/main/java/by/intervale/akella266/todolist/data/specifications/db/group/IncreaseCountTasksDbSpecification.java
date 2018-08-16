package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.repositories.db.dao.GroupDao;

public class IncreaseCountTasksDbSpecification implements Specification<Group, GroupDao> {

    private UUID id;
    private GroupDao mGroupDao;

    public IncreaseCountTasksDbSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public List<Group> getData() {
        Group item = null;
        if ((item = mGroupDao.getGroupById(id.toString())) != null){
            item.increaseCountTasks();
            mGroupDao.update(item);
        }
        return null;
    }

    @Override
    public void setDataSource(GroupDao dataSource) {
        this.mGroupDao = dataSource;
    }
}
