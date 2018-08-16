package by.intervale.akella266.todolist.views.taskDetails;

import android.content.Context;

import java.util.Date;
import java.util.UUID;

import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.specifications.db.group.DecreaseCountTasksDbSpecification;
import by.intervale.akella266.todolist.data.specifications.db.group.IncreaseCountTasksDbSpecification;
import by.intervale.akella266.todolist.data.specifications.db.task.GetTaskByIdDbSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.data.models.TaskItem;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.utils.NotificationSheduler;
import by.intervale.akella266.todolist.utils.Priority;

public class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private Context mContext;
    private Repository<TaskItem> mTaskRepo;
    private Repository<Group> mGroupRepo;
    private TaskDetailsContract.View mDetailsView;
    private TaskItem mTask;
    private boolean isEdit;


    public TaskDetailsPresenter(Context context, TaskDetailsContract.View mDetailsView, UUID itemId) {
        this.mContext = context;
        this.mDetailsView = mDetailsView;
        mTaskRepo = Initializer.getTasksRepo(mContext);
        mGroupRepo = Initializer.getGroupsRepo(mContext);

        if (itemId == null){
            isEdit = false;
            mTask = new TaskItem();
            mTask.setGroupId(UUID.fromString("1-1-1-1-1"));
        }
        else {
            isEdit = true;
            mTask = new TaskItem(mTaskRepo.query(new GetTaskByIdDbSpecification(itemId)).get(0));
        }
    }

    @Override
    public void start() {
        loadTask();
    }

    @Override
    public void loadTask() {
        mDetailsView.showTask(mTask);
    }

    @Override
    public void openDateTimePicker() {
        mDetailsView.showDateTimePicker(mTask.getDate());
    }

    @Override
    public void openChoosingPriority() {
        mDetailsView.showChoosingPriority();
    }

    @Override
    public void openChoosingGroup() {
        mDetailsView.showChoosingGroup();
    }

    @Override
    public void saveTask(String title, String notes) {
        mTask.setTitle(title.trim());
        mTask.setNotes(notes.trim());
        if (isEdit) mTaskRepo.update(mTask);
        else mTaskRepo.add(mTask);
    }

    @Override
    public void removeTask() {
        mTaskRepo.remove(mTask);
        mGroupRepo.query(new DecreaseCountTasksDbSpecification(mTask.getGroupIdUUID()));
    }

    @Override
    public void setDate(Date date) {
        mTask.setDate(date);
        mDetailsView.showDate(date);
    }

    @Override
    public void setPriority(Priority priority) {
        mTask.setPriority(priority);
        mDetailsView.showPriority(priority);
    }

    @Override
    public void setGroup(Group group) {
        updateGroups(group);
        mTask.setGroupId(group.getIdUUID());
        mDetailsView.showGroup(group.getName());
    }

    private void updateGroups(Group group){
        if(!mTask.getIdUUID().equals(group.getIdUUID())) {
            mGroupRepo.query(new DecreaseCountTasksDbSpecification(mTask.getGroupIdUUID()));
            mGroupRepo.query(new IncreaseCountTasksDbSpecification(group.getIdUUID()));
        }
    }

    @Override
    public void setReminder(Context context, boolean b) {
        if (b) {
            mTask.setRemind(true);
            NotificationSheduler.setReminder(context, mTask);
        }
        else {
            mTask.setRemind(false);
            NotificationSheduler.cancelReminder(context, mTask);
        }
    }
}
