package by.intervale.akella266.todolist.data.local.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class RemoveTaskByGroupIdSpecification implements LocalSpecification {

    private UUID mGroupId;

    public RemoveTaskByGroupIdSpecification(UUID mGroupId) {
        this.mGroupId = mGroupId;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> args = new ArrayList<>();
        args.add(mGroupId);
        return new ResponseSpecification(TypeOperation.REMOVE_BY_GROUP_ID, args);
    }
}
