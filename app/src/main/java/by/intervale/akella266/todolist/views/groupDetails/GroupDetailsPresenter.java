package by.intervale.akella266.todolist.views.groupDetails;

import android.content.Context;

import java.util.UUID;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.db.group.GetGroupByIdDbSpecification;
import by.intervale.akella266.todolist.data.specifications.db.task.RemoveTaskByGroupIdDbSpecification;
import by.intervale.akella266.todolist.data.specifications.localJson.group.GetGroupByIdLocalSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.Initializer;

public class GroupDetailsPresenter implements GroupDetailsContract.Presenter{

    private Context mContext;
    private Repository<Group> mGroupRepo;
    private GroupDetailsContract.View mDetailsView;
    private Group mGroup;
    private boolean isEdit;


    public GroupDetailsPresenter(Context context, GroupDetailsContract.View mDetailsView, UUID groupId) {
        this.mDetailsView = mDetailsView;
        this.mContext = context;
        mGroupRepo = Initializer.getGroupsRepo(mContext);

        if (groupId == null){
            mGroup = new Group();
            isEdit = false;
        }
        else{
            isEdit = true;
            mGroup = mGroupRepo.query(new GetGroupByIdDbSpecification(groupId)).get(0);
        }
    }

    @Override
    public void start() {
        loadGroup();
    }

    @Override
    public void loadGroup() {
        mDetailsView.showGroup(mGroup);
    }

    @Override
    public void saveGroup(String name) {
        if (name.isEmpty()){
            mDetailsView.showError(R.string.error_no_title);
        }
        mGroup.setName(name);
        if(isEdit) mGroupRepo.update(mGroup);
        else mGroupRepo.add(mGroup);
    }

    @Override
    public void removeGroup() {
        if (!isEdit) {
            Initializer.getTasksRepo(mContext).query(new RemoveTaskByGroupIdDbSpecification(mGroup.getIdUUID()));
            mGroupRepo.remove(mGroup);
        }
    }
}
