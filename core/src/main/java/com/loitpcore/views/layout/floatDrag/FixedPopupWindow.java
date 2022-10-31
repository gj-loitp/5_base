package com.loitpcore.views.layout.floatDrag;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//17.12.2020 try to revert kotlin but failed

public class FixedPopupWindow extends PopupWindow {

    @Suppress(names = "unused")
    public FixedPopupWindow(Context context) {
        super(context);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(View contentView) {
        super(contentView);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow() {
        super();
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(int width, int height) {
        super(width, height);
    }

    @Suppress(names = "unused")
    public FixedPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public FixedPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    @Override

    public void update(int x, int y, int width, int height, boolean force) {
        if (Build.VERSION.SDK_INT != 24) {
            super.update(x, y, width, height, force);
            return;
        }

        if (width >= 0) {
            setParam("mLastWidth", width);
            setWidth(width);
        }

        if (height >= 0) {
            setParam("mLastHeight", height);
            setHeight(height);
        }

        Object obj = getParam("mContentView");

        View mContentView = null;
        if (obj instanceof View) {
            mContentView = (View) obj;
        }

        if (!isShowing() || mContentView == null) {
            return;
        }

        obj = getParam("mDecorView");
        View mDecorView = null;
        if (obj instanceof View) {
            mDecorView = (View) obj;
        }

        assert mDecorView != null;
        final WindowManager.LayoutParams p = (WindowManager.LayoutParams) mDecorView.getLayoutParams();
        boolean update = force;
        obj = getParam("mWidthMode");
        int mWidthMode = obj != null ? (Integer) obj : 0;
        obj = getParam("mLastWidth");
        int mLastWidth = obj != null ? (Integer) obj : 0;

        final int finalWidth = mWidthMode < 0 ? mWidthMode : mLastWidth;

        if (width != -1 && p.width != finalWidth) {
            p.width = finalWidth;
            setParam("mLastWidth", finalWidth);
            update = true;
        }

        obj = getParam("mHeightMode");
        int mHeightMode = obj != null ? (Integer) obj : 0;
        obj = getParam("mLastHeight");
        int mLastHeight = obj != null ? (Integer) obj : 0;
        final int finalHeight = mHeightMode < 0 ? mHeightMode : mLastHeight;
        if (height != -1 && p.height != finalHeight) {
            p.height = finalHeight;
            setParam("mLastHeight", finalHeight);
            update = true;
        }

        if (p.x != x) {
            p.x = x;
            update = true;
        }

        if (p.y != y) {
            p.y = y;
            update = true;
        }

        obj = execMethod("computeAnimationResource", new Class[]{int.class}, null);

        final int newAnim = obj == null ? 0 : (Integer) obj;
        if (newAnim != p.windowAnimations) {
            p.windowAnimations = newAnim;
            update = true;
        }

        obj = execMethod("computeFlags", new Class[]{int.class}, new Object[]{p.flags});
        final int newFlags = obj == null ? 0 : (Integer) obj;
        if (newFlags != p.flags) {
            p.flags = newFlags;
            update = true;
        }

        if (update) {
            execMethod("setLayoutDirectionFromAnchor", null, null);
            obj = getParam("mWindowManager");
            WindowManager mWindowManager = obj instanceof WindowManager ? (WindowManager) obj : null;
            if (mWindowManager != null) {
                mWindowManager.updateViewLayout(mDecorView, p);
            }
        }
    }

    private Object getParam(String paramName) {

        if (TextUtils.isEmpty(paramName)) {
            return null;
        }
        try {
            Field field = PopupWindow.class.getDeclaredField(paramName);
            field.setAccessible(true);

            return field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setParam(String paramName, Object obj) {
        if (TextUtils.isEmpty(paramName)) {
            return;
        }

        try {
            Field field = PopupWindow.class.getDeclaredField(paramName);
            field.setAccessible(true);
            field.set(this, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object execMethod(String methodName, Class[] cls, Object[] args) {
        if (TextUtils.isEmpty(methodName)) {
            return null;
        }
        try {
            Method method = getMethod(PopupWindow.class, methodName, cls);
            method.setAccessible(true);

            return method.invoke(this, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method getMethod(Class clazz, String methodName, final Class[] classes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName, classes);
                }
            }
        }
        return method;
    }

    @Override
    public void showAsDropDown(View anchorView) {
        if (Build.VERSION.SDK_INT == 24) {

            int[] a = new int[2];
            anchorView.getLocationInWindow(a);
            showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, a[1] + anchorView.getHeight());
        } else {
            super.showAsDropDown(anchorView);
        }
    }
}
