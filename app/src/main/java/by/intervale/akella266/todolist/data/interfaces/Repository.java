package by.intervale.akella266.todolist.data.interfaces;

import java.util.List;

public interface Repository<T> {

    void add(T item);
    void update(T item);
    void remove(T item);
    List<T> query(Specification specification);
}
