package vn.loitp.core.loitp.gallery.member;

import android.os.Bundle;
import androidx.core.view.ViewCompat;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

public class GalleryMemberDetailActivity extends BaseFontActivity {
    private ImageView imageView;
    private TextView tvTitle;
    public static final String PHOTO = "PHOTO";
    public static final String IV = "iv";
    public static final String TV = "tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        imageView = (ImageView) findViewById(R.id.image_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle);
        Photo photo = (Photo) getIntent().getSerializableExtra(PHOTO);
        if (photo != null) {
            //LImageUtil.loadNoAmin(activity, photo.getUrlO(), imageView);
            //tvTitle.setText(photo.getTitle());
            loadItem(photo);
        }
        ViewCompat.setTransitionName(imageView, IV);
        ViewCompat.setTransitionName(tvTitle, TV);
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_member_detail;
    }

    private void loadItem(Photo photo) {
        tvTitle.setText(photo.getTitle());
        LImageUtil.loadNoAmin(activity, photo.getUrlO(), photo.getUrlS(), imageView);
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail(Photo photo) {
        LImageUtil.loadNoAmin(activity, photo.getUrlM(), imageView);
    }

    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage(Photo photo) {
        LImageUtil.loadNoAmin(activity, photo.getUrlO(), photo.getUrlM(), imageView, null);
    }

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    /*private boolean addTransitionListener(final Photo photo) {
        final Transition transition;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage(photo);

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }*/
}
