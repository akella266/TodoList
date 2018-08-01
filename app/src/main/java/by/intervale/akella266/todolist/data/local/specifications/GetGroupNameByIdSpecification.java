package by.intervale.akella266.todolist.data.local.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class GetGroupNameByIdSpecification implements LocalSpecification {

    private UUID id;

    public GetGroupNameByIdSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> resp = new ArrayList<>();
        resp.add(id);
        return new ResponseSpecification(TypeOperation.GET_NAME_GROUP_BY_ID, resp);
    }
}