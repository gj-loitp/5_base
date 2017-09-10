package vn.puresolutions.livestarv3.utilities.v3;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.exception.LSServiceException;


/**
 * @author Khanh Le
 * @version 1.0.0
 * @since Oct 26, 2014
 */
public class AlertMessage {

    protected static final ArrayList<Toast> container = new ArrayList<>();

    public static Toast makeToast(Context context, int resLayoutId, String message) {
        Toast toast = new Toast(context);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(resLayoutId, null);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);

        TextView txtMessage = (TextView) layout.findViewById(R.id.alertMessage_txtMessage);
        if (!TextUtils.isEmpty(message)) {
            txtMessage.setText(message);
        } else {
            txtMessage.setVisibility(View.GONE);
        }

        container.add(toast);

        return toast;
    }

    public static void closeAll() {
        for (Toast t : container) {
            try {
                t.cancel();
            } catch (Throwable e) {
                Log.e(AlertMessage.class.getSimpleName(), "Unable to close toast: " + e.getMessage());
            }
        }
        container.clear();
    }

    public static void showSuccess(Context context, String message) {
        makeToast(context, R.layout.alert_success, message).show();
    }

    public static void showSuccess(Context context, int resId) {
        closeAll();
        showSuccess(context, context.getString(resId));
    }

    public static void showSuccess(Context context) {
        closeAll();
        showSuccess(context, null);
    }

    public static void showError(Context context, String message) {
        makeToast(context, R.layout.view_alert_error, message).show();
    }

    public static void showError(Context context, int resId) {
        closeAll();
        showError(context, context.getString(resId));
    }

    public static void showError(Context context, LSServiceException exception) {
        int resId = context.getResources().getIdentifier(
                String.valueOf(exception.getMessage()), "string", context.getPackageName());
        if (resId != 0) {
            showError(context, context.getString(resId));
        } else {
            showError(context, context.getString(R.string.an_error_has_occurred));
            LLog.d(AlertMessage.class.getName(), "can't found resId with name: " + exception.getMessage());
        }
    }

    // Common error
    public static void showNetworkError(Context context) {
        showError(context, R.string.cant_connect_to_internet);
    }
}
