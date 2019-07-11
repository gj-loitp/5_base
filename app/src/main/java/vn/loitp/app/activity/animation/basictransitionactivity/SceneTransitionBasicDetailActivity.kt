package vn.loitp.app.activity.animation.basictransitionactivity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.widget.ImageView
import android.widget.TextView

import androidx.core.view.ViewCompat

import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil

import loitp.basemaster.R

class SceneTransitionBasicDetailActivity : BaseFontActivity() {

    private lateinit var mHeaderImageView: ImageView
    private lateinit var mHeaderTitle: TextView

    private var mItem: Item? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scene_transition_basic_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = Item.getItem(intent.getIntExtra(EXTRA_PARAM_ID, 0))

        mHeaderImageView = findViewById(R.id.imageview_header)
        mHeaderTitle = findViewById(R.id.textview_title)

        ViewCompat.setTransitionName(mHeaderImageView, VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(mHeaderTitle, VIEW_NAME_HEADER_TITLE)

        loadItem()
    }

    private fun loadItem() {

        mHeaderTitle.text = getString(R.string.image_header, mItem?.name, mItem?.author)

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
            LImageUtil.loadNoAmin(activity, it, mHeaderImageView)
        }
    }

    /**
     * Load the item's full-size image into our [ImageView].
     */
    private fun loadFullSizeImage() {
        mItem?.photoUrl?.let {
            LImageUtil.loadNoAmin(activity, it, mHeaderImageView)
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
        val transition: Transition?
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false
        }
        transition = window.sharedElementEnterTransition
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
