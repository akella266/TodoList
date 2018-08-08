package by.intervale.akella266.todolist.data.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.interfaces.Specification;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupNameByIdSpecification;
import by.intervale.akella266.todolist.data.local.specifications.RemoveTaskByGroupIdSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;

public class GroupLocalRepository implements Repository<Group> {

    private List<Group> mGroups;

    public GroupLocalRepository() {
        this.mGroups = new ArrayList<>();
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
        Initializer.getTasksLocal().query(new RemoveTaskByGroupIdSpecification(item.getId()));
    }

    @Override
    public List<Group> query(Specification specification) {
        LocalSpecification spec = (LocalSpecification)specification;

        ResponseSpecification resp = spec.getType();
        switch (resp.getType()){
            case GET_GROUPS:{
                return mGroups;
            }
            case GET_NAME_GROUP_BY_ID:{
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
