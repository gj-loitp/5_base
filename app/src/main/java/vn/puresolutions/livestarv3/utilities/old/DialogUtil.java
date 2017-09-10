/**
 * 
 */
package vn.puresolutions.livestarv3.utilities.old;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import vn.puresolutions.livestar.R;


public class DialogUtil {

	public static final ProgressDialog createProgressDialog(Context context,
            String message, boolean cancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(cancelable);

        return progressDialog;
    }
	
	public static final ProgressDialog createProgressDialog(Context context,
            String message, boolean cancelable, boolean canceledOnTouchOutside) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        return progressDialog;
    }
	
	public static final ProgressDialog createProgressDialog(Context context,
	        int messageId, boolean cancelable, boolean canceledOnTouchOutside) {        

        return createProgressDialog(context, context.getString(messageId), cancelable, canceledOnTouchOutside);
    }

	public static final ProgressDialog createProgressDialog(Context context,
			int messageId, boolean cancelable) {	

		return createProgressDialog(context, context.getString(messageId), cancelable);
	}

    public static final AlertDialog createAlertDialog(Context context,
                                                      String title, String message, String positiveString,
                                                      String negativeString, DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveString)) {
            builder.setPositiveButton(positiveString, onClickListener);
        }
        if (!TextUtils.isEmpty(negativeString)) {
            builder.setNegativeButton(negativeString, onClickListener);
        }

        return builder.create();
    }

    public static final AlertDialog createAlertDialog(Context context,
                                                      int titleId, int messageId, int positiveStringId,
                                                      int negativeStringId, DialogInterface.OnClickListener onClickListener) {

        return createAlertDialog(context, context.getString(titleId),
                context.getString(messageId),
                context.getString(positiveStringId),
                context.getString(negativeStringId), onClickListener);
    }

    public static final AlertDialog createYesNoAlertDialog(Context context,
                                                           String title, String message, DialogInterface.OnClickListener onClickListener) {

        return createAlertDialog(context, title, message,
                context.getString(R.string.yes),
                context.getString(R.string.no), onClickListener);
    }

    public static final AlertDialog createYesNoAlertDialog(Context context,
                                                           int titleId, int messageId, DialogInterface.OnClickListener onClickListener) {

        return createAlertDialog(context, titleId, messageId,
                R.string.yes, R.string.no, onClickListener);
    }

    public static final AlertDialog createSimpleItemsDialog(Context context, int titleResourceId,
                                                            String[] items, AdapterView.OnItemClickListener onItemClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(titleResourceId));
        // create list view
        ListView modeList = new ListView(context);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, items);
        modeList.setAdapter(modeAdapter);
        modeList.setOnItemClickListener(onItemClickListener);

        builder.setView(modeList);
        return builder.create();
    }
}
