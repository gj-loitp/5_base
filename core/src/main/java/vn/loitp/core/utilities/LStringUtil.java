package vn.loitp.core.utilities;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Created by Loitp on 5/2/2017.
 */

public class LStringUtil {
    public static String convertHTMLTextToPlainText(String htmlText) {
        Spanned spanned;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(htmlText);
        }
        char[] chars = new char[spanned.length()];
        TextUtils.getChars(spanned, 0, spanned.length(), chars, 0);
        return new String(chars);
    }
}
