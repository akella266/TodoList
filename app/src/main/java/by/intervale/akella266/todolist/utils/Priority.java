package by.intervale.akella266.todolist.utils;

public enum Priority {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private int mPriority;

    Priority(int mPriority) {
        this.mPriority = mPriority;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int mPriority) {
        this.mPriority = mPriority;
    }
}
