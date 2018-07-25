package by.intervale.akella266.todolist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Arrays;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.utils.Priority;

public class DialogFragmentPriority extends DialogFragment {

    public interface DialogPriorityListener{
        void onDialogItemClick(DialogFragment dialog, String which);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private DialogPriorityListener mListener;

    public void setListener(DialogPriorityListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderPriority = new AlertDialog.Builder(getActivity());
        final String[] priorities = Arrays.toString(Priority.values()).replaceAll("\\[]","").split(", ");
        builderPriority.setTitle(getString(R.string.dialog_title_date))
                .setItems(priorities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogItemClick(DialogFragmentPriority.this, priorities[i]);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(DialogFragmentPriority.this);
                    }
                });
        return builderPriority.create();
    }
}
