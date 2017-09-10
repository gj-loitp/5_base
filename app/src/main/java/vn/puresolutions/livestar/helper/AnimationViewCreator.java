package vn.puresolutions.livestar.helper;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

public class AnimationViewCreator {

    private Context context;

    public AnimationViewCreator(Context context) {
        this.context = context;
    }

    public AnimationViewHolder createGiftItemView(SimpleDraweeView giftView, String url) {
        /* Image View Gift */
        // get view location
        int[] location = new int[2];
        giftView.getLocationOnScreen(location);
        int width = giftView.getWidth();
        int height = giftView.getHeight();

        // create a new duplication gift item
        final SimpleDraweeView duplicate = new SimpleDraweeView(context);
        duplicate.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        LImageUtils.loadImage(duplicate, url);

        // set size and location
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.leftMargin = location[0];
        params.topMargin = location[1];
        final AnimationViewHolder animation = new AnimationViewHolder(duplicate, AnimationConstraint.Gift.GIFT_IMAGE_ANIMATION, params);
        animation.setListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation aim) {
            }

            @Override
            public void onAnimationEnd(final Animation aim) {
                // Remove view when animation end
                new Handler().post(() -> {
                    ViewGroup parent = (ViewGroup) animation.getAnimationView().getParent();
                    if (parent != null) {
                        parent.removeView(animation.getAnimationView());
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation aim) {
            }
        });

        return animation;
    }

    public AnimationViewHolder createGiftExpView(final View view, long exp) {
        /* Text View Exp *//*
        final TextView txtExp = new TextView(context);
        txtExp.setText(String.format(context.getString(R.string.gain_exp_message), exp));
        txtExp.setTextColor(ContextCompat.getColor(context, R.drawable.selector_gift_exp));
        txtExp.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimension(R.dimen.gift_exp_text_size));
        txtExp.post(() -> {
            // calculating margin left and top
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) txtExp.getLayoutParams();
            int selectedViewHeight = view.getHeight();
            int selectedViewWidth = view.getWidth();

            int[] location = new int[2];
            view.getLocationOnScreen(location);

            params.leftMargin = location[0] + ((selectedViewWidth - txtExp.getWidth()) / 2);
            params.topMargin = location[1] + ((selectedViewHeight - txtExp.getHeight()) / 2) ;
            txtExp.setLayoutParams(params);
        });

        final RelativeLayout.LayoutParams paramsExp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final AnimationViewHolder animation = new AnimationViewHolder(txtExp, AnimationConstraint.Gift.GIFT_EXP_ANIMATION , paramsExp);
        animation.setListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation aim) {}

            @Override
            public void onAnimationEnd(Animation aim) {
                // Remove view when animation end
                new Handler().post(() -> {
                    ViewGroup parent = (ViewGroup) animation.getAnimationView().getParent();
                    if (parent != null) {
                        parent.removeView(animation.getAnimationView());
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation aim) {}
        });

        return animation;*/
        return null;
    }
}
