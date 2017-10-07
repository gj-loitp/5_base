package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import vn.loitp.app.utilities.LImageUtil;
import vn.loitp.flickr.model.Photoset;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Animate(Animation.CARD_LEFT_IN_ASC)
@NonReusable
@Layout(R.layout.item_photo)
public class PhotoItem {

    @View(R.id.imageView)
    private ImageView imageView;

    private Activity activity;
    private Photoset photoset;
    private int position;

    public PhotoItem(Activity activity, Photoset photoset, int position, Callback callback) {
        this.activity = activity;
        this.photoset = photoset;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        LImageUtil.load(activity, photoset.getPrimaryPhotoExtras().getUrlM(), imageView);
    }

    @Click(R.id.imageView)
    private void onClick() {
        if (callback != null) {
            callback.onClick(photoset, position);
        }
    }

    public interface Callback {
        public void onClick(Photoset photoset, int position);
    }

    private Callback callback;
}