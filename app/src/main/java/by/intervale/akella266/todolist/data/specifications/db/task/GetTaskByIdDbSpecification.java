package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class GetTaskByIdDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private UUID itemId;
    private List<TaskItem> mItems;

    public GetTaskByIdDbSpecification(UUID itemId) {
        this.itemId = itemId;
    }

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> tasks = new ArrayList<>();
        for(TaskItem item : mItems){
            if(item.getId().equals(itemId)){
                tasks.add(item);
                return tasks;
            }
        }
        return null;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
