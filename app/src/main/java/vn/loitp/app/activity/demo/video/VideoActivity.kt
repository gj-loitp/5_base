package vn.loitp.app.activity.demo.video

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoRendererEventListener
import kotlinx.android.synthetic.main.activity_demo_video.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_video)
class VideoActivity : BaseFontActivity(), VideoRendererEventListener {
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // 2. Create a default LoadControl
        val loadControl: LoadControl = DefaultLoadControl()

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl)

        //Set media controller
        playerView.useController = true
        playerView.requestFocus()

        // Bind the player to the view.
        playerView.player = player

        // I. ADJUST HERE:
        //CHOOSE CONTENT: LiveStream / SdCard

        //LIVE STREAM SOURCE: * Livestream links may be out of date so find any m3u8 files online and replace:
        //Uri mp4VideoUri =Uri.parse("http://81.7.13.162/hls/ss1/index.m3u8"); //random 720p source
        val mp4VideoUri = Uri.parse("http://54.255.155.24:1935//Live/_definst_/amlst:sweetbcha1novD235L240P/playlist.m3u8") //Radnom 540p indian channel
        //Uri mp4VideoUri =Uri.parse("FIND A WORKING LINK ABD PLUg INTO HERE");


        //VIDEO FROM SD CARD: (2 steps. set up file and path, then change videoSource to get the file)
        //String urimp4 = "path/FileName.mp4"; //upload file to device and add path/name.mp4
        //Uri mp4VideoUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+urimp4);


        //Measures bandwidth during playback. Can be null if not required.
        val bandwidthMeterA = DefaultBandwidthMeter()
        //Produces DataSource instances through which media data is loaded.
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeterA)
        //Produces Extractor instances for parsing the media data.
        //ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // II. ADJUST HERE:

        //This is the MediaSource representing the media to be played:
        //FOR SD CARD SOURCE:
        //MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        //FOR LIVESTREAM LINK:
        val videoSource: MediaSource = HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null)
        val loopingSource = LoopingMediaSource(videoSource)
        player?.prepare(loopingSource)
        player?.addListener(object : ExoPlayer.EventListener {
            override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {}
            override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {
                logD("Listener-onTracksChanged...")
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                logD("Listener-onLoadingChanged...isLoading:$isLoading")
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                logD("Listener-onPlayerStateChanged...$playbackState")
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                logD("Listener-onRepeatModeChanged...")
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPlayerError(error: ExoPlaybackException) {
                logD("Listener-onPlayerError...")
                player?.apply {
                    this.stop()
                    this.prepare(loopingSource)
                    this.playWhenReady = true
                }
            }

            override fun onPositionDiscontinuity(reason: Int) {}
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
                logD("Listener-onPlaybackParametersChanged...")
            }

            override fun onSeekProcessed() {}
        })
        player?.apply {
            this.playWhenReady = true
            this.setVideoDebugListener(this@VideoActivity) //for listening to resolution change and  outputing the resolution
        }

        /*

        //MPD
        //DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"));
        Uri uri = Uri.parse("http://yt-dash-mse-test.commondatastorage.googleapis.com/media/feelings_vp9-20130806-manifest.mpd");
        DashMediaSource dashMediaSource = new DashMediaSource(uri, dataSourceFactory, new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);
        //BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        simpleExoPlayerView.setPlayer(player);
        player.prepare(dashMediaSource);

        */
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
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