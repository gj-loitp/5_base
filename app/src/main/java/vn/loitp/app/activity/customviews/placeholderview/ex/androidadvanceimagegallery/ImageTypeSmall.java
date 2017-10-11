package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.Animation;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.PlaceHolderView;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Animate;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Click;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.LongClick;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.NonReusable;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Animate(Animation.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small)
public class ImageTypeSmall {

    @View(R.id.imageView)
    private ImageView imageView;

    private String mUlr;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeSmall(Context context, PlaceHolderView placeHolderView, String ulr) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mUlr).into(imageView);
    }

    @LongClick(R.id.imageView)
    private void onLongClick() {
        mPlaceHolderView.removeView(this);
    }

    @Click(R.id.imageView)
    private void onClick() {
        ToastUtils.showShort("Touch " + mUlr);
    }
}