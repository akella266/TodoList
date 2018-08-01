package by.intervale.akella266.todolist.utils;

import java.util.Observable;

public class FragmentObserver extends Observable {

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}
