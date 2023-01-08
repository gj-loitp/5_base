package vn.loitp.a.cv.video.exo.mng

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.C.ContentType
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener
import com.loitp.R
import com.loitp.core.ext.*
import com.loitp.core.utils.AppUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class PlayerManager : AdsMediaSource.MediaSourceFactory {
    private var adsLoader: ImaAdsLoader? = null
    private var dataSourceFactory: DataSource.Factory? = null
    var player: SimpleExoPlayer? = null
    private var screenW: Int = 0

    var videoW = 0
    var videoH = 0

    @Suppress("unused")
    val contentPosition: Long
        get() = player?.contentPosition ?: 0

    val duration: Long
        get() = player?.duration ?: 0

    constructor(context: Context) {
        this.adsLoader = null
        this.screenW = screenWidth
        dataSourceFactory = DefaultDataSourceFactory(context, AppUtils.appName)
    }

    constructor(context: Context, urlIMAAd: String) {
        if (urlIMAAd.isNotEmpty()) {
            adsLoader = ImaAdsLoader(context, Uri.parse(urlIMAAd))
        }
        this.screenW = screenWidth
        dataSourceFactory = DefaultDataSourceFactory(context, AppUtils.appName)
    }

    @JvmOverloads
    fun init(
        context: Context,
        playerView: PlayerView,
        linkPlay: String,
        contentPosition: Long = 0
    ) {
        if (linkPlay.isEmpty()) {
            throw IllegalArgumentException("linkPlay is empty")
        }
        playerView.controllerShowTimeoutMs = 8000
        playerView.controllerHideOnTouch = true

        // Create a default track selector.
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        // Bind the player to the view.
        playerView.player = player

        // This is the MediaSource representing the content media (i.e. not the ad).
        // String contentUrl = context.getString(R.string.content_url);
        val contentMediaSource = buildMediaSource(Uri.parse(linkPlay))

        // Compose the content media source into a new AdsMediaSource with both ads and content.
        var mediaSourceWithAds: MediaSource? = null
        adsLoader?.let {
            it.setPlayer(player)
            mediaSourceWithAds = AdsMediaSource(
                contentMediaSource,
                this,
                it,
                playerView
            )
        }

        player?.let {
            // Prepare the player with the source.
            if (mediaSourceWithAds == null) {
                it.prepare(contentMediaSource)
            } else {
                it.prepare(mediaSourceWithAds)
            }
            it.playWhenReady = true
            it.seekTo(contentPosition)
            it.addListener(object : Player.EventListener {
                override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
                ) {
                }

                override fun onLoadingChanged(isLoading: Boolean) {
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            if (playerView.controllerShowTimeoutMs == 0) {
                                playerView.controllerShowTimeoutMs = 8000
                            }
                            if (!playerView.controllerHideOnTouch) {
                                playerView.controllerHideOnTouch = true
                            }
                        }
                        Player.STATE_IDLE -> {
                        }
                        Player.STATE_READY -> {
                        }
                        Player.STATE_ENDED -> {
                            playerView.apply {
                                showController()
                                controllerShowTimeoutMs = 0
                                controllerHideOnTouch = false
                            }
                        }
                    }
                }

                override fun onPlayerError(error: ExoPlaybackException?) {}

                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
            })

            it.addVideoListener(object : VideoListener {
                override fun onVideoSizeChanged(
                    width: Int,
                    height: Int,
                    unappliedRotationDegrees: Int,
                    pixelWidthHeightRatio: Float
                ) {
                    videoW = width
                    videoH = height
                }

                override fun onSurfaceSizeChanged(width: Int, height: Int) {
                }

                override fun onRenderedFirstFrame() {}
            })
        }
    }

    fun reset() {
        player?.let {
            it.release()
            player = null
        }
    }

    fun release() {
        player?.let {
            it.release()
            player = null
        }
        adsLoader?.release()
    }

    // AdsMediaSource.MediaSourceFactory implementation.
    override fun createMediaSource(uri: Uri): MediaSource {
        return buildMediaSource(uri)
    }

    override fun getSupportedTypes(): IntArray {
        // IMA does not support Smooth Streaming ads.
        return intArrayOf(C.TYPE_DASH, C.TYPE_HLS, C.TYPE_OTHER)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return when (@ContentType val type = Util.inferContentType(uri)) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }

    fun toggleFullscreen(activity: Activity) {
        if (activity.isLandscape()) {
            // land -> port
            activity.toggleFullscreen(isFullScreen = false)
            activity.changeScreenPortrait()
        } else {
            // port -> land
            activity.toggleFullscreen(isFullScreen = true)
            activity.changeScreenLandscape()
        }
    }

    // for other sample not UZVideo
    fun updateSizePlayerView(
        playerView: PlayerView,
        exoFullscreen: ImageButton
    ) {
        if (playerView.context.isLandscape()) {
            playerView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            playerView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            exoFullscreen.setImageResource(R.drawable.player_collapse)
        } else {
            playerView.layoutParams.height = screenW * 9 / 16
            exoFullscreen.setImageResource(R.drawable.player_fullscreen)
        }
        playerView.requestLayout()
    }

    fun pauseVideo() {
        player?.playWhenReady = false
    }

    fun resumeVideo() {
        player?.playWhenReady = true
    }

    @Suppress("unused")
    fun seekTo(mls: Long) {
        player?.seekTo(mls)
    }
}
