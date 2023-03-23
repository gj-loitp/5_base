package vn.loitp.up.a.cv.dragView.frm

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import vn.loitp.databinding.FDragViewTopExoPlayerBinding

class ExoPlayerTopFragment : Fragment(), Player.EventListener {

    companion object {
        const val STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
    }

    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var mediaDataSourceFactory: DataSource.Factory? = null
    private lateinit var binding: FDragViewTopExoPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FDragViewTopExoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context)

        mediaDataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            Uri.parse(STREAM_URL)
        )
        simpleExoPlayer?.let {
            it.addListener(this)
            it.prepare(mediaSource, false, false)
            it.playWhenReady = true
        }

        binding.playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()

        binding.aspectRatioFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        binding.aspectRatioFrameLayout.setAspectRatio(16f / 9)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_ENDED) {
            simpleExoPlayer?.playWhenReady = true
        }
    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer?.let {
            it.removeListener(this)
            it.release()
        }
    }
}
