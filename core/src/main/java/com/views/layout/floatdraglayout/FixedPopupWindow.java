package com.views.layout.floatdraglayout;

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

/**
 * FixedPopupWindow
 * 修复 Android 7.0 上系统 API 问题的 PopupWindow
 *
 * @author baishixian
 * @date 2018/1/26 下午2:17
 */

public class FixedPopupWindow extends PopupWindow {
    public FixedPopupWindow(Context context) {
        super(context);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixedPopupWindow(View contentView) {
        super(contentView);
    }

    public FixedPopupWindow() {
        super();
    }

    public FixedPopupWindow(int width, int height) {
        super(width, height);
    }

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

    /**
     * 反射获取对象
     *
     * @return
     * @paramparamName
     */
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

    /**
     * 反射赋值对象
     *
     * @paramparamName
     * @paramobj
     */
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

    /**
     * 反射执行方法
     *
     * @return
     * @parammethodName
     * @paramargs
     */
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

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @paramclazz 目标类
     * @parammethodName 方法名
     * @paramclasses 方法参数类型数组
     * @return方法对象
     * @throwsException
     */
    private Method getMethod(Class clazz, String methodName, final Class[] classes) throws Exception {
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
            showAtLocation(anchorView, Gravity.NO_GRAVITY, 0,
                    a[1] + anchorView.getHeight() + 0);
        } else {
            super.showAsDropDown(anchorView);
        }
    }
}
