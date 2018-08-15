package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class GetTasksByNameDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private String name;
    private List<TaskItem> mItems;

    public GetTasksByNameDbSpecification(String name) {
        this.name = name;
    }

    @Override
    public List<TaskItem> getData() {
        List<TaskItem> tasks = new ArrayList<>();
        if(!name.isEmpty())
            for (TaskItem item : mItems)
                if (item.getTitle().toLowerCase().contains(name.toLowerCase())) tasks.add(item);
        return tasks;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
