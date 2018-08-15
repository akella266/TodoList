package by.intervale.akella266.todolist.views.todo;

import android.content.Context;

import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.localJson.group.GetGroupsLocalSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.Initializer;

public class TodoListPresenter implements TodoListContract.Presenter {

    private Context mContext;
    private Repository<Group> mGroupRepo;
    private TodoListContract.View mTodoListView;

    public TodoListPresenter(Context context, TodoListContract.View mTodoListView) {
        this.mContext = context;
        this.mTodoListView = mTodoListView;
        mGroupRepo = Initializer.getGroupsRepo(mContext);
    }

    @Override
    public void start() {
        loadGroup();
    }

    @Override
    public void loadGroup() {
        mTodoListView.showGroups(mGroupRepo.query(new GetGroupsLocalSpecification()));
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
        mTodoListView.showTasks(group);
    }
}
