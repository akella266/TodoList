package by.intervale.akella266.todolist.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import by.intervale.akella266.todolist.data.models.TaskItem;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_ALARM = "alarm_receiver";

    public static Intent getStartIntent(Context context, TaskItem item){
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ALARM, item);
        intent.putExtra(EXTRA_ALARM, args);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra(EXTRA_ALARM);
        TaskItem item = (TaskItem) bundle.get(EXTRA_ALARM);
        if (item == null) item = new TaskItem();
        NotificationSheduler.showNotification(context, item);
    }
}
