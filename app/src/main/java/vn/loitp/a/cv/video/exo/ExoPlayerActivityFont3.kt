package vn.loitp.a.cv.video.exo

import android.os.Bundle
import com.google.android.exoplayer2.ui.PlayerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.a_video_exo_player3.*
import vn.loitp.R
import vn.loitp.a.cv.video.exo.mng.PlayerManager

@LogTag("ExoPlayerActivity3")
@IsFullScreen(false)
class ExoPlayerActivityFont3 : BaseActivityFont() {
    private var playerManager: PlayerManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_video_exo_player3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)
        playerManager?.init(context = this, playerView = playerView0, linkPlay = linkPlay)

        bt0To1.setSafeOnClickListener {
            playerView0.player?.let { p ->
                PlayerView.switchTargetView(
                    p,
                    playerView0,
                    playerView1
                )
            }
        }
        bt1To0.setSafeOnClickListener {
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

    override fun onBaseBackPressed() {
        if (LScreenUtil.isLandscape()) {
            playerManager?.toggleFullscreen(activity = this)
        } else {
            super.onBaseBackPressed()
        }
    }
}
