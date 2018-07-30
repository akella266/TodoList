package by.intervale.akella266.todolist.data.local.specifications;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;

public class GetCompletedTaskSpecification implements LocalSpecification {
    @Override
    public ResponseSpecification getType() {
        return new ResponseSpecification(TypeOperation.GET_COMPLETED_TASKS, null);
    }
}
