package vn.puresolutions.livestarv3.utilities.old;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

public class VipUtils {
    private static int[] vipColors = {R.color.vip1, R.color.vip2,
            R.color.vip3, R.color.vip4, R.color.vip5};
    private static int[] vipIcons = {R.drawable.ic_vip1, R.drawable.ic_vip2,
            R.drawable.ic_vip3, R.drawable.ic_vip4, R.drawable.ic_vip5};

    public static void styleTextVip(TextView textView, int vip, int defaultColor) {
        if (vip > 0 && vip <= vipColors.length) {
            defaultColor = vipColors[vip - 1];
        }
        ;
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), defaultColor));
    }

    public static void setIconVip(ImageView imgIcon, int vip) {
        if (vip > 0 && vip <= vipIcons.length) {
            imgIcon.setImageResource(vipIcons[vip - 1]);
        } else {
            imgIcon.setImageDrawable(null);
        }
    }

    public static int getVipColor(int vip, int defaultColor) {
        if (vip > 0 && vip <= vipColors.length) {
            defaultColor = vipColors[vip - 1];
        }
        return defaultColor;
    }
}
