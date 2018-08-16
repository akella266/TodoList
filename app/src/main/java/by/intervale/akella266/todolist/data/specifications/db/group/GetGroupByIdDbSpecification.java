package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.repositories.db.dao.GroupDao;

public class GetGroupByIdDbSpecification implements Specification<Group, GroupDao> {

    private UUID id;
    private GroupDao mGroupDao;

    public GetGroupByIdDbSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public List<Group> getData() {
        List<Group> resultList = new ArrayList<>();
        resultList.add(mGroupDao.getGroupById(id.toString()));
        return resultList;
    }

    @Override
    public void setDataSource(GroupDao dataSource) {
        this.mGroupDao = dataSource;
    }
}
