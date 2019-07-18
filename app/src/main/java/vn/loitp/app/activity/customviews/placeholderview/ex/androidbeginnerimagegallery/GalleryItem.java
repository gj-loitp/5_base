package vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.views.placeholderview.Animation;
import com.views.placeholderview.annotations.Animate;
import com.views.placeholderview.annotations.Click;
import com.views.placeholderview.annotations.Layout;
import com.views.placeholderview.annotations.NonReusable;
import com.views.placeholderview.annotations.Resolve;
import com.views.placeholderview.annotations.View;

import loitp.basemaster.R;

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