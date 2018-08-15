package by.intervale.akella266.todolist.data.specifications.db.group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.Group;

public class DecreaseCountTasksDbSpecification implements Specification<Group, List<Group>> {

    private UUID id;
    private List<Group> mItems;

    public DecreaseCountTasksDbSpecification(UUID id) {
        this.id = id;
        mItems = new ArrayList<>();
    }

    @Override
    public List<Group> getData() {
        Group resultGroup = new Group();
        for(Group group : mItems)
            if (group.getId().equals(id)) resultGroup = group;
        resultGroup.decreaseCountTasks();
        return mItems;
    }

    @Override
    public void setDataSource(List<Group> dataSource) {
        this.mItems = dataSource;
    }
}
