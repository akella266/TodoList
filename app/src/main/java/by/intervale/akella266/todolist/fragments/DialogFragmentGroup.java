package by.intervale.akella266.todolist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.data.local.specifications.GetGroupsSpecification;
import by.intervale.akella266.todolist.data.models.Group;
import by.intervale.akella266.todolist.utils.Initializer;

public class DialogFragmentGroup extends DialogFragment {

    public interface DialogGroupListener{
        void onDialogItemClick(DialogFragment dialog, Group which);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private DialogFragmentGroup.DialogGroupListener mListener;

    public void setListener(DialogFragmentGroup.DialogGroupListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderPriority = new AlertDialog.Builder(getContext());
        final List<Group> groups = Initializer.getGroupsLocal().query(new GetGroupsSpecification());
        String[] namesGroup = new String[groups.size()];
        for(int i = 0 ; i < groups.size(); i++)
            namesGroup[i] = groups.get(i).getName();

        builderPriority.setTitle(getString(R.string.dialog_title_group))
                .setItems(namesGroup, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogItemClick(DialogFragmentGroup.this, groups.get(i));
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(DialogFragmentGroup.this);
                    }
                });
        return builderPriority.create();
    }
}
