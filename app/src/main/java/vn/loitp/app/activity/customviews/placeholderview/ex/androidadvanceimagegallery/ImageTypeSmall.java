package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.utils.util.ToastUtils;
import com.views.placeholderview.Animation;
import com.views.placeholderview.PlaceHolderView;
import com.views.placeholderview.annotations.Animate;
import com.views.placeholderview.annotations.Click;
import com.views.placeholderview.annotations.Layout;
import com.views.placeholderview.annotations.LongClick;
import com.views.placeholderview.annotations.NonReusable;
import com.views.placeholderview.annotations.Resolve;
import com.views.placeholderview.annotations.View;

import loitp.basemaster.R;

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