package vn.loitp.core.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www.muathu@gmail.com on 12/29/2017.
 */

public class LDialogUtil {
    private static List<AlertDialog> alertDialogList = new ArrayList<>();

    private static void clearAll() {
        for (int i = 0; i < alertDialogList.size(); i++) {
            if (alertDialogList.get(i) != null) {
                alertDialogList.get(i).cancel();
            }
        }
    }

    public interface Callback1 {
        public void onClick1();
    }

    public static void showDialog1(Context context, String title, String msg, String button1, final Callback1 callback1) {
        clearAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setPositiveButton(button1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback1 != null) {
                    callback1.onClick1();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        alertDialogList.add(dialog);
    }

    public interface Callback2 {
        public void onClick1();

        public void onClick2();
    }

    public static void showDialog2(Context context, String title, String msg, String button1, String button2, final Callback2 callback2) {
        clearAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        if (button1 != null && !button1.isEmpty()) {
            builder.setNegativeButton(button1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback2 != null) {
                        callback2.onClick1();
                    }
                }
            });
        }
        if (button2 != null && !button2.isEmpty()) {
            builder.setPositiveButton(button2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback2 != null) {
                        callback2.onClick2();
                    }
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
        alertDialogList.add(dialog);
    }

    public interface Callback3 {
        public void onClick1();

        public void onClick2();

        public void onClick3();
    }

    public static void showDialog3(Context context, String title, String msg, String button1, String button2, String button3, final Callback3 callback3) {
        clearAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        if (button1 != null && !button1.isEmpty()) {
            builder.setNegativeButton(button1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback3 != null) {
                        callback3.onClick1();
                    }
                }
            });
        }
        if (button2 != null && !button2.isEmpty()) {
            builder.setPositiveButton(button2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback3 != null) {
                        callback3.onClick2();
                    }
                }
            });
        }
        if (button3 != null && !button3.isEmpty()) {
            builder.setNeutralButton(button3, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback3 != null) {
                        callback3.onClick3();
                    }
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
        alertDialogList.add(dialog);
    }

    public interface CallbackList {
        public void onClick(int position);
    }

    public static void showDialogList(Context context, String title, String[] arr, final CallbackList callbackList) {
        clearAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        //String[] arr = {"horse", "cow", "camel", "sheep", "goat"};
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callbackList != null) {
                    callbackList.onClick(which);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        alertDialogList.add(dialog);
    }

    public static void show(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void hide(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
