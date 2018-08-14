package by.intervale.akella266.todolist.views.todo;

import java.util.UUID;

import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupsSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.Initializer;

public class TodoListPresenter implements TodoListContract.Presenter {

    private GroupLocalRepository mGroupRepo;
    private TodoListContract.View mTodoListView;

    public TodoListPresenter(TodoListContract.View mTodoListView) {
        this.mTodoListView = mTodoListView;
        mGroupRepo = Initializer.getGroupsLocal();
    }

    @Override
    public void start() {
        loadGroup();
    }

    @Override
    public void loadGroup() {
        mTodoListView.showGroups(mGroupRepo.query(new GetGroupsSpecification()));
    }

    @Override
    public void openGroupDetails(Group group) {
        mTodoListView.showGroupDetails(group);
    }

    @Override
    public void addNewGroup() {
        mTodoListView.showAddNewGroup();
        loadGroup();
    }

    @Override
    public void removeGroup(Group group) {
        if (group.getId().equals(UUID.fromString("1-1-1-1-1")))
            mTodoListView.showError("This group cannot be removed");
        else {
            mGroupRepo.remove(group);
            loadGroup();
        }
    }

    @Override
    public void openTasks(Group group) {

    }
}
