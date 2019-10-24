package vn.loitp.app.activity.customviews.layout.relativepopupwindow;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.views.LToast;
import com.views.layout.relativepopupwindow.LRelativePopupWindow;

import loitp.basemaster.R;

public class ExampleCardPopupL extends LRelativePopupWindow {

    ExampleCardPopupL(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.popup_card, null);
        setContentView(layout);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Disable default animation for circular reveal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setAnimationStyle(0);
        }
        layout.findViewById(R.id.ll).setOnClickListener(view -> LToast.show(context, "On Click"));
    }

    @Override
    public void showOnAnchor(@NonNull View anchor, int vertPos, int horizPos, int x, int y, boolean fitInScreen) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal(anchor);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularReveal(@NonNull final View anchor) {
        final View contentView = getContentView();
        contentView.post(() -> {
            final int[] myLocation = new int[2];
            final int[] anchorLocation = new int[2];
            contentView.getLocationOnScreen(myLocation);
            anchor.getLocationOnScreen(anchorLocation);
            final int cx = anchorLocation[0] - myLocation[0] + anchor.getWidth() / 2;
            final int cy = anchorLocation[1] - myLocation[1] + anchor.getHeight() / 2;

            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            final int dx = Math.max(cx, contentView.getMeasuredWidth() - cx);
            final int dy = Math.max(cy, contentView.getMeasuredHeight() - cy);
            final float finalRadius = (float) Math.hypot(dx, dy);
            Animator animator = ViewAnimationUtils.createCircularReveal(contentView, cx, cy, 0f, finalRadius);
            animator.setDuration(500);
            animator.start();
        });
    }

}