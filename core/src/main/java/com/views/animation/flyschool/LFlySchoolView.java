package com.views.animation.flyschool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.core.utilities.LImageUtil;

import loitp.core.R;

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
        LImageUtil.INSTANCE.load(getContext(), drawableRes, ivGift);
    }

    @Override
    public void setShape(ImgObject imgObject, int drawableRes) {
        if (imgObject == null || ivGift == null || ivAvatar == null) {
            return;
        }
        //LLog.d(TAG, "drawableRes " + drawableRes);
        if (drawableRes == 0) {
            try {
                String urlGift = imgObject.getUrl();
                LImageUtil.INSTANCE.load(getContext(), urlGift, ivGift);
            } catch (NullPointerException e) {
                //do nothing
            }
        } else {
            LImageUtil.INSTANCE.load(getContext(), drawableRes, ivGift);
        }
        String urlAvatar = imgObject.getAvatar();
        LImageUtil.INSTANCE.load(getContext(), urlAvatar, ivAvatar);
    }
}