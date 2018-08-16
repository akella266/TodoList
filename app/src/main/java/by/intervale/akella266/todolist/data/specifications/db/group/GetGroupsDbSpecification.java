package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.repositories.db.dao.GroupDao;

public class GetGroupsDbSpecification implements Specification<Group, GroupDao> {

    private GroupDao mGroupDao;

    @Override
    public List<Group> getData() {
        return mGroupDao.getGroups();
    }

    @Override
    public void setDataSource(GroupDao dataSource) {
        this.mGroupDao = dataSource;
    }

}
