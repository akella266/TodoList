package by.intervale.akella266.todolist.data.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.Specification;

public class IncreaseCountTasksSpecification implements Specification {

    private UUID id;

    public IncreaseCountTasksSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public ResponseSpecification getType() {
        List<Object> args = new ArrayList<>();
        args.add(id);
        return new ResponseSpecification(TypeOperation.INCREASE_COUNT_TASKS, args);
    }
}
