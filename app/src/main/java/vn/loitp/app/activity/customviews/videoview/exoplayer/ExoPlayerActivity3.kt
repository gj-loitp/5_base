package vn.loitp.app.activity.customviews.videoview.exoplayer

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.google.android.exoplayer2.ui.PlayerView
import com.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player3.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_video_exo_player3)
@LogTag("ExoPlayerActivity3")
@IsFullScreen(false)
class ExoPlayerActivity3 : BaseFontActivity() {
    private var playerManager: PlayerManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linkPlay = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)
        playerManager?.init(context = this, playerView = playerView0, linkPlay = linkPlay)

        bt0To1.setOnClickListener {
            PlayerView.switchTargetView(playerView0.player, playerView0, playerView1)
        }
        bt1To0.setOnClickListener {
            PlayerView.switchTargetView(playerView1.player, playerView1, playerView0)
        }
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
        if (LScreenUtil.isLandscape()) {
            playerManager?.toggleFullscreen(activity = this)
        } else {
            super.onBackPressed()
        }
    }
}
