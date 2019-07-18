package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.core.utilities.LUIUtil;
import com.views.placeholderview.lib.placeholderview.Animation;
import com.views.placeholderview.lib.placeholderview.PlaceHolderView;
import com.views.placeholderview.lib.placeholderview.annotations.Animate;
import com.views.placeholderview.lib.placeholderview.annotations.Layout;
import com.views.placeholderview.lib.placeholderview.annotations.NonReusable;
import com.views.placeholderview.lib.placeholderview.annotations.Resolve;
import com.views.placeholderview.lib.placeholderview.annotations.View;

import java.util.List;

import loitp.basemaster.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Animate(Animation.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small_list)
public class ImageTypeSmallList {

    @View(R.id.placeholderview)
    private PlaceHolderView mPlaceHolderView;

    private Context mContext;
    private List<Image> mImageList;

    public ImageTypeSmallList(Context context, List<Image> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    private void onResolved() {
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        LUIUtil.INSTANCE.setPullLikeIOSHorizontal(mPlaceHolderView);
        for (Image image : mImageList) {
            mPlaceHolderView.addView(new ImageTypeSmall(mContext, mPlaceHolderView, image.getImageUrl()));
        }
    }
}