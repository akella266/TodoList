package by.intervale.akella266.todolist.data.interfaces;

import java.util.List;

public interface Specification<T, K> {
    List<T> getData();
    void setDataSource(K dataSource);
}
