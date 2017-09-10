package vn.puresolutions.livestarv3.utilities.v3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.Item;

/**
 * Created by MobileTV on 2/13/2017.
 */

public class LDialogUtils {
    private static final String TAG = LDialogUtils.class.getSimpleName();

    public static Dialog loadingMultiColor(Activity activity) {
        return loadingMultiColor(activity, true, ContextCompat.getColor(activity, R.color.White));
    }

    public static Dialog loadingMultiColor(Activity activity, int color) {
        return loadingMultiColor(activity, true, color);
    }

    public static Dialog loadingMultiColor(Activity activity, boolean isTransparentBkg) {
        return loadingMultiColor(activity, isTransparentBkg, ContextCompat.getColor(activity, R.color.White));
    }

    public static Dialog loadingMultiColor(Activity activity, boolean isTransparentBkg, int color) {
        Dialog dialog;
        /*if (isTransparentBkg) {
            dialog = new Dialog(activity, android.R.style.Theme_Translucent);
        } else {
            dialog = new Dialog(activity);
        }*/
        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        /*
        //start remove dim background
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //end remove dim background
        */

        dialog.setContentView(R.layout.multi_color_loading);
        final AVLoadingIndicatorView iv = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
        iv.setIndicatorColor(color);

        final FrameLayout flMain = (FrameLayout) dialog.findViewById(R.id.fl_main);
        if (isTransparentBkg) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0.1f);
        } else {
            flMain.setBackgroundResource(R.color.WhiteSmoke);
        }
        return dialog;
    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void hideDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public interface Callback1 {
        public void onClickButton0();
    }

    public interface Callback2 {
        public void onClickButton0();

        public void onClickButton1();
    }

    public interface Callback3 {
        public void onClickButton0();

        public void onClickButton1();

        public void onClickButton2();
    }

    public static void showDlg3Option(final Context context, int iconRes, String titleString, String msgString, String bt0String, String bt1String, String bt2String, final Callback3 callback3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_3_option, null);
        builder.setView(dialogView);
        TextView bt0 = (TextView) dialogView.findViewById(R.id.bt_0);
        TextView bt1 = (TextView) dialogView.findViewById(R.id.bt_1);
        TextView bt2 = (TextView) dialogView.findViewById(R.id.bt_2);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        ImageView ivIcon = (ImageView) dialogView.findViewById(R.id.iv_icon);
        TextView tvBody = (TextView) dialogView.findViewById(R.id.tv_body);
        tvTitle.setText(titleString);
        tvBody.setText(msgString);
        bt0.setText(bt0String);
        bt1.setText(bt1String);
        bt2.setText(bt2String);
        if (iconRes == 0) {
            ivIcon.setVisibility(View.GONE);
        } else {
            ivIcon.setImageResource(iconRes);
            ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.primaryColor));
        }
        final AlertDialog dialog = builder.create();
        bt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback3 != null) {
                    callback3.onClickButton0();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback3 != null) {
                    callback3.onClickButton1();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback3 != null) {
                    callback3.onClickButton2();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void showDlg2Option(final Context context, int iconRes, String titleString, String msgString, String bt0String, String bt1String, final Callback2 callback2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_2_option, null);
        builder.setView(dialogView);
        TextView bt0 = (TextView) dialogView.findViewById(R.id.bt_0);
        TextView bt1 = (TextView) dialogView.findViewById(R.id.bt_1);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        ImageView ivIcon = (ImageView) dialogView.findViewById(R.id.iv_icon);
        TextView tvBody = (TextView) dialogView.findViewById(R.id.tv_body);
        tvTitle.setText(titleString);
        tvBody.setText(msgString);
        bt0.setText(bt0String);
        bt1.setText(bt1String);
        if (iconRes == 0) {
            ivIcon.setVisibility(View.GONE);
        } else {
            ivIcon.setImageResource(iconRes);
        }
        final AlertDialog dialog = builder.create();
        bt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback2 != null) {
                    callback2.onClickButton0();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback2 != null) {
                    callback2.onClickButton1();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void showDlg1Option(final Context context, int iconRes, String titleString, String msgString, String bt0String, final Callback1 callback1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_1_option, null);
        builder.setView(dialogView);
        TextView bt0 = (TextView) dialogView.findViewById(R.id.bt_0);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        ImageView ivIcon = (ImageView) dialogView.findViewById(R.id.iv_icon);
        TextView tvBody = (TextView) dialogView.findViewById(R.id.tv_body);
        tvTitle.setText(titleString);
        tvBody.setText(msgString);
        bt0.setText(bt0String);
        if (iconRes == 0) {
            ivIcon.setVisibility(View.GONE);
        } else {
            ivIcon.setImageResource(iconRes);
            //ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.primaryColor));
        }
        final AlertDialog dialog = builder.create();
        bt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (callback1 != null) {
                    callback1.onClickButton0();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //start dialog no coin
    public interface CallbackDialogNoCoin {
        public void onCancel();

        public void onGetCoin();
    }

    public static void showDialogNoCoin(final Context context, Item item, CallbackDialogNoCoin callbackDialogNoCoin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_no_coin, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        SimpleDraweeView ivGift = (SimpleDraweeView) dialogView.findViewById(R.id.iv_gift);
        LImageUtils.loadImage(ivGift, item.getImage());
        TextView tvCoin = (TextView) dialogView.findViewById(R.id.tv_coin);
        tvCoin.setText(item.getPrice() + " xu");
        TextView btCancel = (TextView) dialogView.findViewById(R.id.bt_cancel);
        TextView btGetCoin = (TextView) dialogView.findViewById(R.id.bt_get_coin);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackDialogNoCoin != null) {
                    callbackDialogNoCoin.onCancel();
                }
                dialog.cancel();
            }
        });
        btGetCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackDialogNoCoin != null) {
                    callbackDialogNoCoin.onGetCoin();
                }
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    //end dialog no coin

    ///////////////////////////////////////////////////////////////////////////////////////
    //start dialog buy package
    public interface CallbackDialogBuyPackage {
        public void onCancel();

        public void onOkay();
    }

    public static void showDialogBuyPackage(final Context context, String packageName, String packageprice, CallbackDialogBuyPackage callbackDialogBuyPackage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buy_package, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        TextView btCancel = (TextView) dialogView.findViewById(R.id.bt_cancel);
        TextView btOk = (TextView) dialogView.findViewById(R.id.bt_ok);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView tvPrice = (TextView) dialogView.findViewById(R.id.tv_price);
        tvTitle.setText(packageName);
        tvPrice.setText(packageprice);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackDialogBuyPackage != null) {
                    callbackDialogBuyPackage.onCancel();
                }
                dialog.cancel();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackDialogBuyPackage != null) {
                    callbackDialogBuyPackage.onOkay();
                }
                dialog.cancel();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    //end dialog buy package

    public interface OnDateSet {
        public void onDateSet(int year, int month, int day);
    }

    public interface OnTimeSet {
        public void onTimeSet(int hour, int minute);
    }

    public static void showDatePicker(final Context context, OnDateSet onDateSet) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                onDateSet.onDateSet(year, month, dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static void showTimePicker(final Context context, OnTimeSet onTimeSet) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                onTimeSet.onTimeSet(hourOfDay, minute);
            }
        }, hour, minutes, false);
        timePickerDialog.show();
    }
}