package by.intervale.akella266.todolist.data.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.specifications.RemoveTaskByGroupIdSpecification;
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
            if (mGroups.get(i).getId().equals(item.getId())){
                mGroups.set(i, item);
                break;
            }
        }
    }

    @Override
    public void remove(Group item) {
        mGroups.remove(item);
        Initializer.getTasksRepo(mContext).query(new RemoveTaskByGroupIdSpecification(item.getId()));
    }

    @Override
    public List<Group> query(Specification spec) {
        ResponseSpecification resp = spec.getType();
        switch (resp.getType()){
            case GET_GROUPS:{
                return mGroups;
            }
            case GET_GROUP_BY_ID:{
                UUID id = (UUID) resp.getArgs().get(0);
                Group resultGroup = new Group();
                for(Group group : mGroups)
                    if (group.getId().equals(id)) resultGroup = group;
                List<Group> resultList = new ArrayList<>();
                resultList.add(resultGroup);
                return resultList;
            }
            default:
                return null;
        }
    }
}
