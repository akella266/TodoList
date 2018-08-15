package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class GetCurrentTasksDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private List<TaskItem> mItems;

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> currentTasks = new ArrayList<>();
        for (TaskItem item : mItems)
            if (!item.isComplete()) currentTasks.add(item);
        return currentTasks;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
