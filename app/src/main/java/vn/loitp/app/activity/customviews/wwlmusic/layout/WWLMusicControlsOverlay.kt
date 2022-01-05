package vn.loitp.app.activity.customviews.wwlmusic.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import vn.loitp.app.R

class WWLMusicControlsOverlay(context: Context) : FrameLayout(context), View.OnClickListener {

    private val layoutControls: RelativeLayout
    private val btPlayerCollapse: ImageView
    private val layoutBottomEndContainer: ViewGroup
    private val btFullscreen: ImageView
    private var listener: Listener? = null
    private var isHided = false

    override fun onClick(v: View) {
        if (v === btPlayerCollapse) {
            listener?.doCollapse()
        } else if (v === btFullscreen) {
            listener?.doFullscreen(!btFullscreen.isSelected)
        }
    }

    fun showControls() {
        layoutControls.visibility = VISIBLE
        isHided = false
    }

    fun hideControls() {
        layoutControls.visibility = GONE
        isHided = true
    }

    fun setListener(l: Listener?) {
        listener = l
    }

    fun switchFullscreen(selected: Boolean) {
        btFullscreen.isSelected = selected
    }

    fun toggleControls() {
        if (isHided) {
            showControls()
        } else {
            hideControls()
        }
    }

    interface Listener {
        fun doCollapse()
        fun doFullscreen(selected: Boolean)
    }

    init {
        clipToPadding = false
        LayoutInflater.from(context).inflate(R.layout.wwl_music_default_controls_overlay, this)
        layoutControls = findViewById(R.id.layoutControls)
        btPlayerCollapse = findViewById(R.id.btPlayerCollapse)
        btPlayerCollapse.setOnClickListener(this)
        layoutBottomEndContainer = findViewById(R.id.layoutBottomEndContainer)
        btFullscreen = layoutBottomEndContainer.findViewById(R.id.btFullscreen)
        btFullscreen.setOnClickListener(this)
    }
}
