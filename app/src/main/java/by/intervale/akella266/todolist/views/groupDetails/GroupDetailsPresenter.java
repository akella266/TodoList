package by.intervale.akella266.todolist.views.groupDetails;

import android.support.design.widget.Snackbar;

import java.util.UUID;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.local.GroupLocalRepository;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupByIdSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;

public class GroupDetailsPresenter implements GroupDetailsContract.Presenter{

    private GroupLocalRepository mGroupRepo;
    private GroupDetailsContract.View mDetailsView;
    private Group mGroup;
    private boolean isEdit;


    public GroupDetailsPresenter(GroupDetailsContract.View mDetailsView, UUID groupId) {
        this.mDetailsView = mDetailsView;
        mGroupRepo = Initializer.getGroupsLocal();

        if (groupId == null){
            mGroup = new Group();
            isEdit = false;
        }
        else{
            isEdit = true;
            mGroup = mGroupRepo.query(new GetGroupByIdSpecification(groupId)).get(0);
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
        if (!isEdit)
            mGroupRepo.remove(mGroup);
    }
}
