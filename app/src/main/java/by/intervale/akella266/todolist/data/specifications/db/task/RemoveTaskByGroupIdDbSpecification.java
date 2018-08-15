package by.intervale.akella266.todolist.data.specifications.db.task;

import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.models.TaskItem;

public class RemoveTaskByGroupIdDbSpecification implements Specification<TaskItem, List<TaskItem>> {

    private UUID mGroupId;
    private List<TaskItem> mItems;

    public RemoveTaskByGroupIdDbSpecification(UUID mGroupId) {
        this.mGroupId = mGroupId;
    }

    @Override
    public List<TaskItem> getData() {
        for(int i = 0; i < mItems.size(); i++){
            if (mItems.get(i).getGroupId().equals(mGroupId)){
                mItems.remove(i);
                i--;
            }
        }
        return mItems;
    }

    @Override
    public void setDataSource(List<TaskItem> dataSource) {
        this.mItems = dataSource;
    }
}
