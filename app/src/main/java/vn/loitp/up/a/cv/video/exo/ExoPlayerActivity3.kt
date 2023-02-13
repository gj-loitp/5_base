package vn.loitp.up.a.cv.video.exo

import android.os.Bundle
import com.google.android.exoplayer2.ui.PlayerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isLandscape
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.AVideoExoPlayer3Binding
import vn.loitp.up.a.cv.video.exo.mng.PlayerManager

@LogTag("ExoPlayerActivity3")
@IsFullScreen(false)
class ExoPlayerActivity3 : BaseActivityFont() {
    private var playerManager: PlayerManager? = null
    private lateinit var binding: AVideoExoPlayer3Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVideoExoPlayer3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)
        playerManager?.init(context = this, playerView = binding.playerView0, linkPlay = linkPlay)

        binding.bt0To1.setSafeOnClickListener {
            binding.playerView0.player?.let { p ->
                PlayerView.switchTargetView(
                    /* player = */ p,
                    /* oldPlayerView = */ binding.playerView0,
                    /* newPlayerView = */ binding.playerView1
                )
            }
        }
        binding.bt1To0.setSafeOnClickListener {
            binding.playerView1.player?.let { p ->
                PlayerView.switchTargetView(
                    /* player = */ p,
                    /* oldPlayerView = */ binding.playerView1,
                    /* newPlayerView = */ binding.playerView0
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
        if (isLandscape()) {
            playerManager?.toggleFullscreen(activity = this)
        } else {
            super.onBaseBackPressed()
        }
    }
}
