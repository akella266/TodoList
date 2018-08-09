package by.intervale.akella266.todolist.inbox;

import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.TaskItemLocalRepository;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.utils.Initializer;

public class InboxPresenter implements InboxContract.Presenter {

    private TaskItemLocalRepository mTasksRepo;
    private GroupLocalRepository mGroupRepo;
    private InboxContract.View mInboxView;


    public InboxPresenter(InboxContract.View view) {
        mTasksRepo = Initializer.getTasksLocal();
        mGroupRepo = Initializer.getGroupsLocal();
        mInboxView = view;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void loadTasks() {
//        mInboxView.showTasks(mTasksRepo.getAllTasks());
    }

    @Override
    public void getTasks() {

    }

    @Override
    public void openTaskDetails(TaskItem task) {

    }


}
