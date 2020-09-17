package vn.loitp.app.activity.customviews.videoview.exoplayer

import android.content.res.Configuration
import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player2.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import vn.loitp.app.R

//custom UI exo_playback_control_view.xml

@LayoutId(R.layout.activity_video_exo_player2)
class ExoPlayerActivity2 : BaseFontActivity() {
    private var playerManager: PlayerManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerView.useController = false
        val linkPlay = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(activity)

        controls.showTimeoutMs = 0
        playerManager?.init(context = this, playerView = playerView, linkPlay = linkPlay)
        controls.player = playerManager?.player
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

    override fun onResume() {
        super.onResume()
        playerManager?.resumeVideo()
    }

    public override fun onPause() {
        super.onPause()
        playerManager?.pauseVideo()
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
