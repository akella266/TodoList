package by.intervale.akella266.todolist.data;

import java.util.List;

public class ResponseSpecification {

    private TypeOperation mType;
    private List<Object> args;

    public ResponseSpecification(TypeOperation mType, List<Object> args) {
        this.mType = mType;
        this.args = args;
    }

    public TypeOperation getType() {
        return mType;
    }

    public void setType(TypeOperation mType) {
        this.mType = mType;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}
