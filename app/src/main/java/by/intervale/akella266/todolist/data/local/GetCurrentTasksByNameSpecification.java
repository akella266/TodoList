package by.intervale.akella266.todolist.data.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class GetCurrentTasksByNameSpecification implements LocalSpecification {

    private String name;

    public GetCurrentTasksByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> resp = new ArrayList<>();
        resp.add(name);
        return new ResponseSpecification(TypeOperation.GET_CURRENT_BY_NAME, resp);
    }
}
