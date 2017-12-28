package vn.loitp.views.animation.flyschool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import loitp.core.R;
import vn.loitp.core.utilities.LImageUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LFlySchoolView extends RelativeLayout implements ShapeSetter {
    private final String TAG = getClass().getSimpleName();
    private SimpleDraweeView ivGift;
    private SimpleDraweeView ivAvatar;
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

        this.ivGift = (SimpleDraweeView) findViewById(R.id.iv_gift);
        this.ivAvatar = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        //this.tvName = (TextView) findViewById(R.id.tv_name);
    }

    @Override
    public void setShape(int drawable) {
        LImageUtil.loadImage(ivGift, drawable);
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
                LImageUtil.loadImage(ivGift, urlGift);
            } catch (NullPointerException e) {
                //do nothing
            }
        } else {
            LImageUtil.loadImage(ivGift, drawableRes);
        }
        String urlAvatar = imgObject.getAvatar();
        LImageUtil.loadImage(ivAvatar, urlAvatar);
    }
}