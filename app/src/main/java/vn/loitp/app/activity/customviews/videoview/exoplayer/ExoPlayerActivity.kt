package vn.loitp.app.activity.customviews.videoview.exoplayer

import android.content.res.Configuration
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LScreenUtil
import com.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import vn.loitp.app.R

//custom UI exo_playback_control_view.xml
class ExoPlayerActivity : BaseFontActivity() {
    private var playerManager: PlayerManager? = null
    private var linkPlay = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linkPlay = intent.getStringExtra(Constants.KEY_VIDEO_LINK_PLAY) ?: ""
        val linkIMAAd = intent.getStringExtra(Constants.KEY_VIDEO_LINK_IMA_AD)

        playerManager = linkIMAAd?.let {
            PlayerManager(activity, it)
        } ?: PlayerManager(activity)
        playerManager?.updateSizePlayerView(activity = activity, playerView = playerView, exoFullscreen = exo_fullscreen)
        exo_fullscreen.setOnClickListener {
            playerManager?.toggleFullscreen(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_exo_player
    }

    public override fun onResume() {
        super.onResume()
        playerManager?.init(context = this, playerView = playerView, linkPlay = linkPlay)
    }

    public override fun onPause() {
        super.onPause()
        playerManager?.reset()
    }

    public override fun onDestroy() {
        playerManager?.release()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (LScreenUtil.isLandscape(activity)) {
            playerManager?.toggleFullscreen(activity)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager?.updateSizePlayerView(activity = activity, playerView = playerView, exoFullscreen = exo_fullscreen)
        } else {
            playerManager?.updateSizePlayerView(activity = activity, playerView = playerView, exoFullscreen = exo_fullscreen)
        }
    }
}