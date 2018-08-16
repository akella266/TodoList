package by.intervale.akella266.todolist.data.repositories.db;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.repositories.db.dao.GroupDao;

public class GroupDbRepository implements Repository<Group> {

    private GroupDao mGroupDao;

    public GroupDbRepository(GroupDao mGroupDao) {
        this.mGroupDao = mGroupDao;
    }

    @Override
    public void add(Group item) {
        mGroupDao.insert(item);
    }

    @Override
    public void update(Group item) {
        mGroupDao.update(item);
    }

    @Override
    public void remove(Group item) {
        mGroupDao.delete(item);
    }

    @Override
    public List<Group> query(Specification specification) {
        specification.setDataSource(mGroupDao);
        return specification.getData();
    }
}
