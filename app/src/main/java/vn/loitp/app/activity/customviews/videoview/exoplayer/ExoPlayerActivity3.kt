package vn.loitp.app.activity.customviews.videoview.exoplayer

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.google.android.exoplayer2.ui.PlayerView
import com.loitpcore.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player3.*
import vn.loitp.app.R

@LogTag("ExoPlayerActivity3")
@IsFullScreen(false)
class ExoPlayerActivity3 : BaseFontActivity() {
    private var playerManager: PlayerManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_exo_player3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)
        playerManager?.init(context = this, playerView = playerView0, linkPlay = linkPlay)

        bt0To1.setOnClickListener {
            playerView0.player?.let { p ->
                PlayerView.switchTargetView(
                    p,
                    playerView0,
                    playerView1
                )
            }
        }
        bt1To0.setOnClickListener {
            playerView1.player?.let { p ->
                PlayerView.switchTargetView(
                    p,
                    playerView1,
                    playerView0
                )
            }
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
