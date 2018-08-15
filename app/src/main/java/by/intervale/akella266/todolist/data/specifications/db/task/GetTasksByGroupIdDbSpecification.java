package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class GetTasksByGroupIdDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private UUID groupId;
    private List<TaskItem> mItems;

    public GetTasksByGroupIdDbSpecification(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> tasks = new ArrayList<>();
        for(TaskItem item : mItems){
            if(item.getGroupId().equals(groupId)){
                tasks.add(item);
            }
        }
        return tasks;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
