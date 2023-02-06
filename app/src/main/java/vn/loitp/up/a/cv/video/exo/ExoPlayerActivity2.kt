package vn.loitp.up.a.cv.video.exo

import android.content.res.Configuration
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isLandscape
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import vn.loitp.databinding.AVideoExoPlayer2Binding
import vn.loitp.up.a.cv.video.exo.mng.PlayerManager

// custom UI exo_playback_control_view.xml

@LogTag("ExoPlayerActivity2")
@IsFullScreen(false)
class ExoPlayerActivity2 : BaseActivityFont() {
    private var playerManager: PlayerManager? = null
    private lateinit var binding: AVideoExoPlayer2Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVideoExoPlayer2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.playerView.useController = false
        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)

        binding.controls.showTimeoutMs = 0
        playerManager?.init(context = this, playerView = binding.playerView, linkPlay = linkPlay)
        binding.controls.player = playerManager?.player
        playerManager?.updateSizePlayerView(
            playerView = binding.playerView,
            exoFullscreen = exo_fullscreen
        )
        exo_fullscreen.setOnClickListener {
            playerManager?.toggleFullscreen(this)
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
        if (this.isLandscape()) {
            playerManager?.toggleFullscreen(this)
        } else {
            super.onBaseBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager?.updateSizePlayerView(
                playerView = binding.playerView,
                exoFullscreen = exo_fullscreen
            )
        } else {
            playerManager?.updateSizePlayerView(
                playerView = binding.playerView,
                exoFullscreen = exo_fullscreen
            )
        }
    }
}
