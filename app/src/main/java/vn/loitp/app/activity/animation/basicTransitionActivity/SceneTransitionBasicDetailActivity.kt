package vn.loitp.app.activity.animation.basicTransitionActivity

import android.os.Bundle
import android.transition.Transition
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_scene_transition_basic_details.*
import vn.loitp.app.R

@LogTag("SceneTransitionBasicDetailActivity")
@IsFullScreen(false)
class SceneTransitionBasicDetailActivity : BaseFontActivity() {

    companion object {

        // Extra name for the ID parameter
        const val EXTRA_PARAM_ID = "detail:_id"

        // View name of the header image. Used for activity scene transitions
        const val VIEW_NAME_HEADER_IMAGE = "detail:header:image"

        // View name of the header title. Used for activity scene transitions
        const val VIEW_NAME_HEADER_TITLE = "detail:header:title"
    }

    private var mItem: Item? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_scene_transition_basic_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        loadItem()
    }

    private fun setupViews() {
        mItem = Item.getItem(intent.getIntExtra(EXTRA_PARAM_ID, 0))

        ViewCompat.setTransitionName(imageViewHeader, VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(textViewTitle, VIEW_NAME_HEADER_TITLE)
    }

    private fun loadItem() {

        textViewTitle.text = getString(R.string.image_header, mItem?.name, mItem?.author)

        // If we're running on Lollipop and we have added a listener to the shared element
        // transition, load the thumbnail. The listener will load the full-size image when
        // the transition is complete.
        loadThumbnail()
    }

    /**
     * Load the item's thumbnail image into our [ImageView].
     */
    private fun loadThumbnail() {
        mItem?.photoUrl?.let {
            LImageUtil.load(context = this, any = it, imageView = imageViewHeader)
        }
    }

    /**
     * Load the item's full-size image into our [ImageView].
     */
    private fun loadFullSizeImage() {
        mItem?.photoUrl?.let {
            LImageUtil.load(context = this, any = it, imageView = imageViewHeader)
        }
    }

    /**
     * Try and add a [Transition.TransitionListener] to the entering shared element
     * [Transition]. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    @Suppress("unused")
    private fun addTransitionListener(): Boolean {
        val transition = window.sharedElementEnterTransition
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(
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
                }
            )
            return true
        }

        // If we reach here then we have not added a listener
        return false
    }
}
