package by.intervale.akella266.todolist.data.local.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class GetTasksByGroupIdSpecification implements LocalSpecification {

    private UUID groupId;

    public GetTasksByGroupIdSpecification(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> list = new ArrayList<>();
        list.add(groupId);
        return new ResponseSpecification(TypeOperation.GET_BY_GROUP_ID_TASKS, list);
    }
}
