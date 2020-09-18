package vn.loitp.app.activity.animation.basictransitionactivity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_scene_transition_basic_details.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_animation_scene_transition_basic_details)
@LogTag("SceneTransitionBasicDetailActivity")
class SceneTransitionBasicDetailActivity : BaseFontActivity() {
    private var mItem: Item? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = Item.getItem(intent.getIntExtra(EXTRA_PARAM_ID, 0))

        ViewCompat.setTransitionName(imageViewHeader, VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(textViewTitle, VIEW_NAME_HEADER_TITLE)

        loadItem()
    }

    private fun loadItem() {

        textViewTitle.text = getString(R.string.image_header, mItem?.name, mItem?.author)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            // If we're running on Lollipop and we have added a listener to the shared element
            // transition, load the thumbnail. The listener will load the full-size image when
            // the transition is complete.
            loadThumbnail()
        } else {
            // If all other cases we should just load the full-size image now
            loadFullSizeImage()
        }
    }

    /**
     * Load the item's thumbnail image into our [ImageView].
     */
    private fun loadThumbnail() {
        mItem?.photoUrl?.let {
            LImageUtil.loadNoAmin(activity, it, imageViewHeader)
        }
    }

    /**
     * Load the item's full-size image into our [ImageView].
     */
    private fun loadFullSizeImage() {
        mItem?.photoUrl?.let {
            LImageUtil.loadNoAmin(activity, it, imageViewHeader)
        }
    }

    /**
     * Try and add a [Transition.TransitionListener] to the entering shared element
     * [Transition]. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    private fun addTransitionListener(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false
        }
        val transition = window.sharedElementEnterTransition
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(@TargetApi(Build.VERSION_CODES.KITKAT)
            object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage()

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition) {
                    // No-op
                }

                override fun onTransitionCancel(transition: Transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition) {
                    // No-op
                }

                override fun onTransitionResume(transition: Transition) {
                    // No-op
                }
            })
            return true
        }

        // If we reach here then we have not added a listener
        return false
    }

    companion object {

        // Extra name for the ID parameter
        const val EXTRA_PARAM_ID = "detail:_id"

        // View name of the header image. Used for activity scene transitions
        const val VIEW_NAME_HEADER_IMAGE = "detail:header:image"

        // View name of the header title. Used for activity scene transitions
        const val VIEW_NAME_HEADER_TITLE = "detail:header:title"
    }

}
