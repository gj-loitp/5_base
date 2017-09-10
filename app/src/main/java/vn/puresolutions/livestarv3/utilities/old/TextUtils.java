package vn.puresolutions.livestarv3.utilities.old;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

public class TextUtils {
    /**
     * move cursor of editText answer to end
     */
    public static void moveCursorEdtAnswerToEnd(EditText editText) {
        editText.setSelection(editText.getText().length());
    }

    /**
     * add text to edit text and move cursor after content you just added
     *
     * @param editText
     * @param strAppendText
     */
    public static void addTextToEditTextAndMoveCursorAfter(EditText editText, String strAppendText) {
        int startSelectionPosition = editText.getSelectionStart();
        int endSelectionPosition = editText.getSelectionEnd();
        Log.e("TAG", String.format("start: %d, end: %d", startSelectionPosition, endSelectionPosition));
        String strContent = editText.getText().toString();
        String beforeText = strContent.substring(0, startSelectionPosition);
        String afterText = strContent.substring(endSelectionPosition, strContent.length());

        String contentToSet = beforeText + strAppendText + afterText;
        int maxLength = getMaxLengthForEditText(editText);
        //XXX Triet H.M. Pham: Change the way to insert new text
        if ((maxLength > 0 && contentToSet.length() < maxLength) || (maxLength < 0)) {
            editText.getText().delete(startSelectionPosition, endSelectionPosition);
            editText.getText().insert(startSelectionPosition, strAppendText);
        }
    }

    /**
     * http://stackoverflow.com/questions/8069015/how-to-get-edittext-maxlength-
     * setting-in-code/22755012#22755012
     *
     * @param editText
     * @return
     */
    public static int getMaxLengthForEditText(EditText editText) {
        int maxLength = -1;

        for (InputFilter filter : editText.getFilters()) {
            if (filter instanceof InputFilter.LengthFilter) {
                try {
                    Field maxLengthField = filter.getClass().getDeclaredField("mMax");
                    maxLengthField.setAccessible(true);

                    if (maxLengthField.isAccessible()) {
                        maxLength = maxLengthField.getInt(filter);
                    }
                } catch (IllegalAccessException e) {
                    Log.w(filter.getClass().getName(), e);
                } catch (IllegalArgumentException e) {
                    Log.w(filter.getClass().getName(), e);
                } catch (NoSuchFieldException e) {
                    Log.w(filter.getClass().getName(), e);
                } // if an Exception is thrown, Log it and return -1
            }
        }

        return maxLength;
    }

    /**
     * hide key board if no view is focus
     *
     * @param activity
     */
    public static void hideKeyboardWhenNotFocus(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboardWhenFocus(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view != null) {
            inputManager.showSoftInput(view,
                    InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void forceToHideKeyboard(Activity activity, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
