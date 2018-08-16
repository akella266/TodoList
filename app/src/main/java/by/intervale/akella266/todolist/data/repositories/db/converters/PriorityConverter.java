package by.intervale.akella266.todolist.data.repositories.db.converters;

import android.arch.persistence.room.TypeConverter;

import by.intervale.akella266.todolist.utils.Priority;

public class PriorityConverter {

    @TypeConverter
    public static Priority toPriority(int priority) {
        switch (priority){
            case 0: return Priority.NONE;
            case 1: return Priority.LOW;
            case 2: return Priority.MEDIUM;
            case 3: return Priority.HIGH;
            default: throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(Priority priority) {
        return priority.getPriority();
    }
}
