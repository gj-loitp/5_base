package vn.loitp.app.activity.demo.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Surface
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.video.VideoRendererEventListener
import kotlinx.android.synthetic.main.activity_demo_video.*
import vn.loitp.app.R

@LogTag("VideoActivity")
@IsFullScreen(false)
class VideoActivity : BaseFontActivity(), VideoRendererEventListener {
    private var player: SimpleExoPlayer? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_video
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO
    }

    override fun onVideoEnabled(counters: DecoderCounters) {
        logD("onVideoEnabled")
    }

    override fun onVideoDecoderInitialized(decoderName: String, initializedTimestampMs: Long, initializationDurationMs: Long) {
        logD("onVideoDecoderInitialized")
    }

    override fun onVideoInputFormatChanged(format: Format) {
        logD("onVideoInputFormatChanged")
    }

    override fun onDroppedFrames(count: Int, elapsedMs: Long) {
        logD("onDroppedFrames")
    }

    @SuppressLint("SetTextI18n")
    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
        logD("onVideoSizeChanged [ width: $width height: $height]")
        tvResolution.text = "RES:(WxH):${width}X${height}p"
    }

    override fun onRenderedFirstFrame(surface: Surface?) {
        logD("onRenderedFirstFrame")
    }

    override fun onVideoDisabled(counters: DecoderCounters) {
        logD("onVideoDisabled")
    }

    override fun onDestroy() {
        player?.release()
        super.onDestroy()
        logD("onDestroy()...")
    }
}
