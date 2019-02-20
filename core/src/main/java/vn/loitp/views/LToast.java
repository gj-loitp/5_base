package vn.loitp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;

public class LToast {
    private final static String TAG = LToast.class.getSimpleName();

    public static void show(Context context, String s) {
        show(context, s, 0);
    }

    public static void show(Context context, int resource) {
        show(context, resource, 0);
    }

    @SuppressLint("InflateParams")
    public static void showShort(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT, R.drawable.bkg_horizontal);
    }

    @SuppressLint("InflateParams")
    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG, R.drawable.bkg_horizontal);
    }

    @SuppressLint("InflateParams")
    public static void showShort(Context context, String msg, int backgroundRes) {
        show(context, msg, Toast.LENGTH_SHORT, backgroundRes);
    }

    @SuppressLint("InflateParams")
    public static void showShort(Context context, int msgRes, int backgroundRes) {
        show(context, context.getString(msgRes), Toast.LENGTH_SHORT, backgroundRes);
    }

    @SuppressLint("InflateParams")
    public static void showLong(Context context, String msg, int backgroundRes) {
        show(context, msg, Toast.LENGTH_LONG, backgroundRes);
    }

    @SuppressLint("InflateParams")
    public static void showLong(Context context, int msgRes, int backgroundRes) {
        show(context, context.getString(msgRes), Toast.LENGTH_LONG, backgroundRes);
    }

    @SuppressLint("InflateParams")
    public static void show(Context context, int resource, int length) {
        show(context, context.getResources().getString(resource), length, R.drawable.bkg_horizontal);
    }

    @SuppressLint("InflateParams")
    public static void show(Context context, String msg, int length) {
        show(context, msg, length, R.drawable.bkg_horizontal);
    }

    @SuppressLint("InflateParams")
    public static void show(Context context, int resource, int length, int backgroundRes) {
        show(context, context.getResources().getString(resource), length, backgroundRes);
    }

    @SuppressLint("InflateParams")
    public static void show(Context context, String msg, int length, int backgroundRes) {
        clear();
        try {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inf.inflate(R.layout.l_toast, null);
            TextView textView = (TextView) layout.findViewById(R.id.tv_loading);
            textView.setText(msg);
            textView.setBackgroundResource(backgroundRes);
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(length);
            toast.setView(layout);
            toast.show();
            toastList.add(toast);
        } catch (Exception e) {
            LLog.d(TAG, "LToast" + e.toString());
        }
    }

    private static List<Toast> toastList = new ArrayList<>();

    private static void clear() {
        for (int i = 0; i < toastList.size(); i++) {
            if (toastList.get(i) != null) {
                toastList.get(i).cancel();
            }
        }
    }
}
