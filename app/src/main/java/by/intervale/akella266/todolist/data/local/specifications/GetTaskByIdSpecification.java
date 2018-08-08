package by.intervale.akella266.todolist.data.local.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class GetTaskByIdSpecification implements LocalSpecification {

    private UUID itemId;

    public GetTaskByIdSpecification(UUID itemId) {
        this.itemId = itemId;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> args = new ArrayList<>();
        args.add(itemId);
        return new ResponseSpecification(TypeOperation.GET_BY_ID_TASK, args);
    }
}
