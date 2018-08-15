package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;

public class IncreaseCountTasksDbSpecification implements Specification<Group, List<Group>> {

    private UUID id;
    private List<Group> mItems;

    public IncreaseCountTasksDbSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public List<Group> getData() {
        Group resultGroup = new Group();
        for(Group group : mItems)
            if (group.getId().equals(id)) resultGroup = group;
        resultGroup.increaseCountTasks();
        return mItems;
    }

    @Override
    public void setDataSource(List<Group> dataSource) {
        this.mItems = dataSource;
    }
}
