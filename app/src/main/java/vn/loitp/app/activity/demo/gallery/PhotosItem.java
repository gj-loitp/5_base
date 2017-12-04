package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import loitp.basemaster.R;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Click;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Layout;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.NonReusable;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Resolve;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.View;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.SCALE_UP_ASC)
@NonReusable
@Layout(R.layout.item_img)
public class PhotosItem {

    @View(R.id.imageView)
    private ImageView imageView;

    @View(R.id.tv)
    private TextView tv;

    private Activity activity;
    private Photo photo;
    private int position;

    public PhotosItem(Activity activity, Photo photo, int position, Callback callback) {
        this.activity = activity;
        this.photo = photo;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        LImageUtil.load(activity, photo.getUrlO(), imageView, 50, 80);
        String s = "Original size: " + photo.getWidthO() + "x" + photo.getHeightO();
        tv.setText(s);
    }

    @Click(R.id.imageView)
    private void onClick() {
        if (callback != null) {
            callback.onClick(photo, position);
        }
    }

    public interface Callback {
        public void onClick(Photo photo, int position);
    }

    private Callback callback;
}