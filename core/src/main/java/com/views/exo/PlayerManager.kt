package com.views.exo

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageButton
import com.core.utilities.LActivityUtil
import com.core.utilities.LLog
import com.core.utilities.LScreenUtil
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.C.ContentType
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
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
import com.utils.util.AppUtils
import loitp.core.R

class PlayerManager : AdsMediaSource.MediaSourceFactory {
    private val TAG = javaClass.simpleName
    private var adsLoader: ImaAdsLoader? = null
    private var dataSourceFactory: DataSource.Factory? = null
    var player: SimpleExoPlayer? = null
        private set
    private var screenW: Int = 0

    var videoW = 0
        private set
    var videoH = 0
        private set

    val contentPosition: Long
        get() = player?.contentPosition ?: 0

    val duration: Long
        get() = player?.duration ?: 0

    constructor(context: Context) {
        this.adsLoader = null
        this.screenW = LScreenUtil.getScreenWidth()
        dataSourceFactory = DefaultDataSourceFactory(context, AppUtils.getAppName())
    }

    constructor(context: Context, urlIMAAd: String) {
        if (!urlIMAAd.isEmpty()) {
            adsLoader = ImaAdsLoader(context, Uri.parse(urlIMAAd))
        }
        this.screenW = LScreenUtil.getScreenWidth()
        dataSourceFactory = DefaultDataSourceFactory(context, AppUtils.getAppName())
    }

    @JvmOverloads
    fun init(context: Context, playerView: PlayerView, linkPlay: String, contentPosition: Long = 0) {
        if (linkPlay.isEmpty()) {
            LLog.e(TAG, "init failed -> return")
            return
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
        //String contentUrl = context.getString(R.string.content_url);
        val contentMediaSource = buildMediaSource(Uri.parse(linkPlay))

        // Compose the content media source into a new AdsMediaSource with both ads and content.
        var mediaSourceWithAds: MediaSource?
        adsLoader.let {
            mediaSourceWithAds = AdsMediaSource(
                    contentMediaSource,
                    /* adMediaSourceFactory= */ this,
                    adsLoader,
                    playerView.overlayFrameLayout)
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
            LLog.d(TAG, "seekTo contentPosition: $contentPosition")
            it.addListener(object : Player.EventListener {
                override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}

                override fun onLoadingChanged(isLoading: Boolean) {
                    //LLog.d(TAG, "onLoadingChanged " + isLoading);
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            //LLog.d(TAG, "onPlayerStateChanged STATE_BUFFERING");
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
                            //LLog.d(TAG, "onPlayerStateChanged STATE_ENDED");
                            playerView.apply {
                                showController()
                                controllerShowTimeoutMs = 0
                                controllerHideOnTouch = false
                            }
                        }
                    }
                    //LLog.d(TAG, "onPlayerStateChanged STATE_IDLE");
                    //LLog.d(TAG, "onPlayerStateChanged STATE_READY");
                }

                override fun onPlayerError(error: ExoPlaybackException?) {}

                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
            })

            it.addVideoListener(object : VideoListener {
                override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int,
                                                pixelWidthHeightRatio: Float) {
                    videoW = width
                    videoH = height
                }

                override fun onSurfaceSizeChanged(width: Int, height: Int) {
                    //LLog.d(TAG, "onSurfaceSizeChanged " + width + "x" + height);
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
        @ContentType val type = Util.inferContentType(uri)
        when (type) {
            C.TYPE_DASH -> return DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> return SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }

    fun toggleFullscreen(activity: Activity) {
        if (LScreenUtil.isLandscape(activity)) {
            //land -> port
            LScreenUtil.toggleFullscreen(activity, false)
            LActivityUtil.changeScreenPortrait(activity)
        } else {
            //port -> land
            LScreenUtil.toggleFullscreen(activity, true)
            LActivityUtil.changeScreenLandscape(activity)
        }
    }

    //for other sample not UZVideo
    fun updateSizePlayerView(activity: Activity, playerView: PlayerView, exoFullscreen: ImageButton) {
        //LLog.d(TAG, "updateSizePlayerView screenW " + screenW);
        if (LScreenUtil.isLandscape(activity)) {
            playerView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            playerView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            exoFullscreen.setImageResource(R.drawable.exo_controls_fullscreen_exit)
        } else {
            playerView.layoutParams.height = screenW * 9 / 16
            exoFullscreen.setImageResource(R.drawable.exo_controls_fullscreen_enter)
        }
        playerView.requestLayout()
        //LLog.d(TAG, "requestLayout");
    }

    fun pauseVideo() {
        player?.playWhenReady = false
    }

    fun resumeVideo() {
        player?.playWhenReady = true
    }

    fun seekTo(mls: Long) {
        player?.seekTo(mls)
    }
}