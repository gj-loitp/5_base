package vn.loitp.app.activity.customviews.placeholderview.androidbeginnerimagegallery;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Animate(Animation.SCALE_UP_ASC)
@NonReusable
@Layout(R.layout.gallery_item)
public class GalleryItem {

    @View(R.id.imageView)
    private ImageView imageView;

    private Drawable mDrawable;
    private int position;

    public GalleryItem(Drawable drawable, int position, Callback callback) {
        mDrawable = drawable;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        imageView.setImageDrawable(mDrawable);
    }

    @Click(R.id.imageView)
    private void onClick() {
        if (callback != null) {
            callback.onClick(position);
        }
    }

    public interface Callback {
        public void onClick(int position);
    }

    private Callback callback;
}