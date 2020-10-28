package com.views.animation.flyschool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.R;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.core.utilities.LImageUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LFlySchoolView extends RelativeLayout implements ShapeSetter {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivGift;
    private ImageView ivAvatar;
    //private TextView tvName;

    public LFlySchoolView(Context context) {
        super(context);
        init();
    }

    public LFlySchoolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LFlySchoolView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_fly_school, this);

        this.ivGift = (ImageView) findViewById(R.id.iv_gift);
        this.ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        //this.tvName = (TextView) findViewById(R.id.tv_name);
    }

    @Override
    public void setShape(int drawableRes) {
        LImageUtil.Companion.load(getContext(), drawableRes, ivGift, R.color.colorPrimary, R.color.red, null);
    }

    @Override
    public void setShape(ImgObject imgObject, int drawableRes) {
        if (imgObject == null || ivGift == null || ivAvatar == null) {
            return;
        }
        if (drawableRes == 0) {
            try {
                String urlGift = imgObject.getUrl();
                LImageUtil.Companion.load(getContext(), urlGift, ivGift, R.color.colorPrimary, R.color.red, new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            LImageUtil.Companion.load(getContext(), drawableRes, ivGift, R.color.colorPrimary, R.color.red, null);
        }
        String urlAvatar = imgObject.getAvatar();
        LImageUtil.Companion.load(getContext(), urlAvatar, ivAvatar, R.color.colorPrimary, R.color.red, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
    }
}
