package com.core.loitp.gallery.member

import android.os.Bundle
import android.transition.Transition
import android.widget.ImageView
import android.widget.TextView

import androidx.core.view.ViewCompat
import com.R

import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo

class GalleryMemberDetailActivity : BaseFontActivity() {
    private lateinit var imageView: ImageView
    private lateinit var tvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        imageView = findViewById(R.id.image_view)
        tvTitle = findViewById(R.id.tv_title)
        LUIUtil.setTextShadow(tvTitle)
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo)
        ViewCompat.setTransitionName(imageView, IV)
        ViewCompat.setTransitionName(tvTitle, TV)
    }

    override fun setFullScreen(): Boolean {
        return true
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_member_detail
    }

    private fun loadItem(photo: Photo) {
        tvTitle.text = photo.title
        LImageUtil.loadNoAmin(activity, photo.urlO, photo.urlS, imageView)
    }

    /**
     * Load the item's thumbnail image into our [ImageView].
     */
    private fun loadThumbnail(photo: Photo) {
        LImageUtil.loadNoAmin(activity, photo.urlM, imageView)
    }

    /**
     * Load the item's full-size image into our [ImageView].
     */
    private fun loadFullSizeImage(photo: Photo) {
        LImageUtil.loadNoAmin(activity, photo.urlO, photo.urlM, imageView, null)
    }

    companion object {
        const val PHOTO = "PHOTO"
        const val IV = "iv"
        const val TV = "tv"
    }

    /**
     * Try and add a [Transition.TransitionListener] to the entering shared element
     * [Transition]. We do this so that we can load the full-size image after the transition
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
