package by.intervale.akella266.todolist.data.repositories.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.specifications.localJson.task.RemoveTaskByGroupIdLocalSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.Initializer;

public class GroupLocalRepository implements Repository<Group> {

    private Context mContext;
    private List<Group> mGroups;

    public GroupLocalRepository(Context context) {
        this.mGroups = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public void add(Group item) {
        mGroups.add(item);
    }

    @Override
    public void update(Group item) {
        for(int i = 0; i < mGroups.size(); i++){
            if (mGroups.get(i).getIdUUID().equals(item.getIdUUID())){
                mGroups.set(i, item);
                break;
            }
        }
    }

    @Override
    public void remove(Group item) {
        mGroups.remove(item);
    }

    @Override
    public List<Group> query(Specification spec) {
        spec.setDataSource(mGroups);
        return (List<Group>) spec.getData();
    }
}
