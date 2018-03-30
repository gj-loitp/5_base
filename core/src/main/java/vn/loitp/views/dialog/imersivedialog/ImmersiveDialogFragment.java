package vn.loitp.views.dialog.imersivedialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import vn.loitp.views.LToast;

/**
 * Created by LENOVO on 3/30/2018.
 */

public class ImmersiveDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Example Dialog")
                .setMessage("Some text.")
                .create();

        // Temporarily set the dialogs window to not focusable to prevent the short
        // popup of the navigation bar.
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LToast.show(getActivity(), "Touch OK");
            }
        });
        //int color = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
        return alertDialog;
    }

    public void showImmersive(Activity activity) {

        // Show the dialog.
        show(activity.getFragmentManager(), null);

        // It is necessary to call executePendingTransactions() on the FragmentManager
        // before hiding the navigation bar, because otherwise getWindow() would raise a
        // NullPointerException since the window was not yet created.
        getFragmentManager().executePendingTransactions();

        // Hide the navigation bar. It is important to do this after show() was called.
        // If we would do this in onCreateDialog(), we would get a requestFeature()
        // error.
        getDialog().getWindow().getDecorView().setSystemUiVisibility(
                getActivity().getWindow().getDecorView().getSystemUiVisibility()
        );

        // Make the dialogs window focusable again.
        getDialog().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        );

    }

}