package com.views.layout.floatdraglayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;

/**
 * DragLayout
 *
 * @author baishixian
 * @date 2017/12/7 下午3:43
 */

//TODO convert kotlin
public class DisplayUtil {

    /**
     * 获取屏幕显示内容像素宽度（不包括虚拟按键的高度）
     *
     * @param context
     * @return 屏幕显示内容像素宽度
     */
    public static int getScreenContentWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕显示内容像素高度（不包括虚拟按键的高度）
     *
     * @param context
     * @return 屏幕显示内容像素高度
     */
    public static int getScreenContentHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕物理尺寸像素宽度
     *
     * @param context
     * @return 屏幕物理尺寸像素宽度
     */
    public static int getScreenHardwareWidth(Context context) {
        int contentWidth = getScreenContentWidth(context);
        if (isLandscape(context)) {
            return contentWidth + getNavigationBarHeight(context);
        } else {
            return contentWidth;
        }
    }

    /**
     * 获取屏幕物理尺寸像素高度
     *
     * @param context
     * @return 屏幕物理尺寸像素高度
     */
    public static int getScreenHardwareHeight(Context context) {
        int contentHeight = getScreenContentHeight(context);
        if (isLandscape(context)) {
            return contentHeight;
        } else {
            return contentHeight + getNavigationBarHeight(context);
        }
    }

    /**
     * 判断是否横屏
     *
     * @param context
     * @return 是否横屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        Resources resources = context.getResources();
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = resources.getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
            //获取status_bar_height资源的ID
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusHeight = resources.getDimensionPixelSize(resourceId);
            }
        }
        return statusHeight;
    }


    /**
     * 获取虚拟按键栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static int dp2px(Context context, int dps) {
        return Math.round(((float) dps) * getDensityDpiScale(context));
    }

    public static int px2dp(Context context, int pixels) {
        return Math.round(((float) pixels) / getDensityDpiScale(context));
    }

    private static float getDensityDpiScale(Context context) {
        return context.getResources().getDisplayMetrics().xdpi / 160.0f;
    }

    /**
     * 把指定类型的值转换成px值
     *
     * @param unit COMPLEX_UNIT_PX:
     *             COMPLEX_UNIT_DIP:
     *             COMPLEX_UNIT_SP:
     *             COMPLEX_UNIT_PT:
     *             COMPLEX_UNIT_IN:
     *             COMPLEX_UNIT_MM:
     */
    public static int applyDimension(int unit, float value) {
        return (int) TypedValue.applyDimension(unit, value, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 返回当前屏幕是否为竖屏。
     *
     * @return 当且仅当当前屏幕为竖屏时返回true, 否则返回false。
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }
}
