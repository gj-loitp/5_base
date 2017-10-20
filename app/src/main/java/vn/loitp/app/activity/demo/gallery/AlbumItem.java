package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Click;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.NonReusable;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.app.utilities.LImageUtil;
import vn.loitp.flickr.model.photosetgetlist.Photoset;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.SCALE_UP_ASC)
@NonReusable
@Layout(R.layout.item_img)
public class AlbumItem {

    @View(R.id.imageView)
    private ImageView imageView;

    @View(R.id.tv)
    private TextView tv;

    private Activity activity;
    private Photoset photoset;
    private int position;

    public AlbumItem(Activity activity, Photoset photoset, int position, Callback callback) {
        this.activity = activity;
        this.photoset = photoset;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        LImageUtil.load(activity, photoset.getPrimaryPhotoExtras().getUrlM(), imageView, 50, 80);
        String s = photoset.getTitle().getContent() + " (" + photoset.getPhotos() + ")";
        tv.setText(s);
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