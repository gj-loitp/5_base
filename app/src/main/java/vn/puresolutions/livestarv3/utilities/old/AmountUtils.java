package vn.puresolutions.livestarv3.utilities.old;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.widget.TextView;

import com.rockerhieu.emojicon.EmojiconSpan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import vn.puresolutions.livestar.R;

public class AmountUtils {
    public static final String PATTERN = "###,###.##";

    public static String format(double value) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(PATTERN, otherSymbols);
        return ((value != 0d) ? decimalFormat.format(value) : "0");
    }

    public static String toMoney(double value) {
        return  format(value) + "đ";
    }

    public static CharSequence toCoin(TextView textView, double value, boolean hasBackground) {
        Spannable text = new SpannableString("  " + format(value));
        int size = (int) textView.getTextSize();
        EmojiconSpan span = new EmojiconSpan(textView.getContext(), R.drawable.ic_coin, size,
                DynamicDrawableSpan.ALIGN_BASELINE, size);
        text.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(text);
        if (hasBackground) {
            textView.setBackgroundResource(R.drawable.background_text_view_money);
            Resources res = textView.getContext().getResources();
            int paddingLeft = res.getDimensionPixelSize(R.dimen.text_view_money_left_padding);
            int paddingRight = res.getDimensionPixelSize(R.dimen.text_view_money_right_padding);
            textView.setPadding(paddingLeft, 0 , paddingRight , 0);
        }
        return text;
    }

    public static CharSequence toCoin(TextView textView, double value) {
        return toCoin(textView, value, false);
    }

    public static CharSequence toCoin(TextView textView, int price, float discount, boolean hasBackground) {
        float discountPrice = price - (price * discount / 100);
        return toCoin(textView, discountPrice, hasBackground);
    }

    public static CharSequence toCoin(TextView textView, int price, float discount) {
        return toCoin(textView, price, discount, false);
    }

    public static void formatCoinText(TextView textView, int formatId, double value) {
        String coinFormat = "@ " + format(value);
        String text = textView.getContext().getString(formatId, coinFormat);
        Spannable spannable = new SpannableString(text);
        int size = (int) textView.getTextSize();
        EmojiconSpan span = new EmojiconSpan(textView.getContext(), R.drawable.ic_coin, size, DynamicDrawableSpan.ALIGN_BASELINE, size);
        int index = text.indexOf('@');
        spannable.setSpan(span, index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }
}
