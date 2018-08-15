package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;

public class GetGroupsDbSpecification implements Specification<Group, List<Group>> {

    private List<Group> mItems;

    @Override
    public List<Group> getData() {
        return mItems;
    }

    @Override
    public void setDataSource(List<Group> dataSource) {
        this.mItems = dataSource;
    }
}
