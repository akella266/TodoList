package by.intervale.akella266.todolist.data.local.specifications;

import by.intervale.akella266.todolist.data.ResponseSpecification;
import by.intervale.akella266.todolist.data.interfaces.local.LocalSpecification;
import by.intervale.akella266.todolist.data.TypeOperation;

public class GetCurrentTasksSpecification implements LocalSpecification {
    @Override
    public ResponseSpecification getType() {return new ResponseSpecification(TypeOperation.GET_CURRENT_TASKS, null);}
}
