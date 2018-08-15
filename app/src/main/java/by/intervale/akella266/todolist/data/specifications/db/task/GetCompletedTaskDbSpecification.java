package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class GetCompletedTaskDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private List<TaskItem> mItems;

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> completeTasks = new ArrayList<>();
        for (TaskItem item : mItems)
            if (item.isComplete()) completeTasks.add(item);
        return completeTasks;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
