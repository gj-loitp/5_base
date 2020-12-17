package com.views.navigation.arcnavigationview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;

/**
 * Created by rom4ek on 10.01.2017.
 */

public class LArcNavigationView extends NavigationView {

    private static int THRESHOLD;

    private LArcViewSettings settings;
    private int height = 0;
    private int width = 0;
    private Path clipPath, arcPath;

    public LArcNavigationView(Context context) {
        super(context);
        init(context, null);
    }

    public LArcNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        settings = new LArcViewSettings(context, attrs);
        settings.setElevation(ViewCompat.getElevation(this));

        setBackgroundColor(Color.TRANSPARENT);
        setInsetsColor(Color.TRANSPARENT);
        THRESHOLD = Math.round(LArcViewSettings.dpToPx(getContext(), 15)); //some threshold for child views while remeasuring them
    }

    private void setInsetsColor(final int color) {
        try {
            final Field insetForegroundField = ScrimInsetsFrameLayout.class.getDeclaredField("mInsetForeground");
            insetForegroundField.setAccessible(true);
            final ColorDrawable colorDrawable = new ColorDrawable(color);
            insetForegroundField.set(this, colorDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RtlHardcoded")
    private Path createClipPath() {
        final Path path = new Path();
        arcPath = new Path();

        final float arcWidth = settings.getArcWidth();
        final DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) getLayoutParams();
        if (settings.isCropInside()) {
            if (layoutParams.gravity == Gravity.START || layoutParams.gravity == Gravity.LEFT) {
                arcPath.moveTo(width, 0);
                arcPath.quadTo(width - arcWidth, height / 2,
                        width, height);
                arcPath.close();

                path.moveTo(0, 0);
                path.lineTo(width, 0);
                path.quadTo(width - arcWidth, height / 2,
                        width, height);
                path.lineTo(0, height);
                path.close();
            } else if (layoutParams.gravity == Gravity.END || layoutParams.gravity == Gravity.RIGHT) {
                arcPath.moveTo(0, 0);
                arcPath.quadTo(arcWidth, height / 2,
                        0, height);
                arcPath.close();

                path.moveTo(width, 0);
                path.lineTo(0, 0);
                path.quadTo(arcWidth, height / 2,
                        0, height);
                path.lineTo(width, height);
                path.close();
            }
        } else {
            if (layoutParams.gravity == Gravity.START || layoutParams.gravity == Gravity.LEFT) {
                arcPath.moveTo(width - arcWidth / 2, 0);
                arcPath.quadTo(width + arcWidth / 2, height / 2,
                        width - arcWidth / 2, height);
                arcPath.close();

                path.moveTo(0, 0);
                path.lineTo(width - arcWidth / 2, 0);
                path.quadTo(width + arcWidth / 2, height / 2,
                        width - arcWidth / 2, height);
                path.lineTo(0, height);
                path.close();
            } else if (layoutParams.gravity == Gravity.END || layoutParams.gravity == Gravity.RIGHT) {
                arcPath.moveTo(arcWidth / 2, 0);
                arcPath.quadTo(-arcWidth / 2, height / 2,
                        arcWidth / 2, height);
                arcPath.close();

                path.moveTo(width, 0);
                path.lineTo(arcWidth / 2, 0);
                path.quadTo(-arcWidth / 2, height / 2,
                        arcWidth / 2, height);
                path.lineTo(width, height);
                path.close();
            }
        }
        return path;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayoutAndChildren();
        }
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        if (child instanceof NavigationMenuView) {
            child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
                    MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                    getMeasuredHeight(), MeasureSpec.EXACTLY));
        } else {
            super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        }
    }

    private void calculateLayoutAndChildren() {
        if (settings == null) {
            return;
        }
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {
            clipPath = createClipPath();
            ViewCompat.setElevation(this, settings.getElevation());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setOutlineProvider(new ViewOutlineProvider() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void getOutline(View view, Outline outline) {
                        if (clipPath.isConvex()) {
                            outline.setConvexPath(clipPath);
                        }
                    }
                });
            }

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View v = getChildAt(i);

                if (v instanceof NavigationMenuView) {
                    v.setBackground(settings.getBackgroundDrawable());
                    ViewCompat.setElevation(v, settings.getElevation());
                    //TODO: adjusting child views to new width in their rightmost/leftmost points related to path
//                    adjustChildViews((ViewGroup) v);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    @SuppressLint("RtlHardcoded")
    private void adjustChildViews(ViewGroup container) {
        final int containerChildCount = container.getChildCount();
        final PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        final DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) getLayoutParams();

        for (int i = 0; i < containerChildCount; i++) {
            View child = container.getChildAt(i);
            if (child instanceof ViewGroup) {
                adjustChildViews((ViewGroup) child);
            } else {
                final float[] pathCenterPointForItem = {0f, 0f};
                Rect location = locateView(child);
                int halfHeight = location.height() / 2;

                pathMeasure.getPosTan(location.top + halfHeight, pathCenterPointForItem, null);
                if (layoutParams.gravity == Gravity.END || layoutParams.gravity == Gravity.RIGHT) {
                    int centerPathPoint = getMeasuredWidth() - Math.round(pathCenterPointForItem[0]);
                    if (child.getMeasuredWidth() > centerPathPoint) {
                        child.measure(MeasureSpec.makeMeasureSpec(centerPathPoint - THRESHOLD,
                                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                                child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                        child.layout(centerPathPoint + THRESHOLD, child.getTop(), child.getRight(), child.getBottom());
                    }
                } else if (layoutParams.gravity == Gravity.START || layoutParams.gravity == Gravity.LEFT) {
                    if (child.getMeasuredWidth() > pathCenterPointForItem[0]) {
                        child.measure(MeasureSpec.makeMeasureSpec((Math.round(pathCenterPointForItem[0]) - THRESHOLD),
                                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                                child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                        child.layout(child.getLeft(), child.getTop(), (Math.round(pathCenterPointForItem[0]) - THRESHOLD), child.getBottom());
                    }
                }
                //set text ellipsize to end to prevent it from overlapping edge
                if (child instanceof TextView) {
                    ((TextView) child).setEllipsize(TextUtils.TruncateAt.END);
                }
            }
        }
    }

    private Rect locateView(View view) {
        final Rect loc = new Rect();
        final int[] location = new int[2];
        if (view == null) {
            return loc;
        }
        view.getLocationOnScreen(location);

        loc.left = location[0];
        loc.top = location[1];
        loc.right = loc.left + view.getWidth();
        loc.bottom = loc.top + view.getHeight();
        return loc;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}
