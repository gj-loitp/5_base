package vn.loitp.app.activity.demo.floatingvideo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoRendererEventListener
import vn.loitp.app.R

/**
 * Created by loitp on 3/27/2018.
 */
class FloatingViewVideoService : Service(), VideoRendererEventListener {
    private val logTag = javaClass.simpleName

    private var mWindowManager: WindowManager? = null
    private lateinit var mFloatingView: View
    private var playerView: SimpleExoPlayerView? = null
    private var player: SimpleExoPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_demo_floating_video, null)
        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        //Specify the view position
        params.gravity = Gravity.TOP or Gravity.START //Initially view will be added to top-left corner
        params.x = 0
        params.y = 100

        //Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)

        val ivClose = mFloatingView.findViewById<ImageView>(R.id.ivClose)
        playerView = mFloatingView.findViewById(R.id.playerView)
        val rlMove = mFloatingView.findViewById<RelativeLayout>(R.id.rlMove)

        //Set the close button
        ivClose.setOnClickListener {
            //close the service and remove the from from the window
            stopSelf()
        }

        playVideo()

        //Drag and move floating view using user's touch action.
        rlMove.setOnTouchListener(object : OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {

                        //remember the initial position.
                        initialX = params.x
                        initialY = params.y

                        //get the touch location
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        val xDiff = (event.rawX - initialTouchX).toInt()
                        val yDiff = (event.rawY - initialTouchY).toInt()
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()

                        //Update the layout with new X & Y coordinate
                        mWindowManager?.updateViewLayout(mFloatingView, params)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onDestroy() {
        player?.release()
        mWindowManager?.removeView(mFloatingView)
        super.onDestroy()
    }

    //Set the view while floating view is expanded.
    //Set the play button.
    //Open the application on thi button click
    private fun openApp() {
        //Open the application  click.
        val intent = Intent(this@FloatingViewVideoService, FloatingWidgetActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        //close the service and remove view from the view hierarchy
        stopSelf()
    }

    private fun playVideo() {
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // 2. Create a default LoadControl
        val loadControl: LoadControl = DefaultLoadControl()

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl)

        //Set media controller
        playerView?.useController = false
        playerView?.requestFocus()

        // Bind the player to the view.
        playerView?.player = player

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
//                Log.d(logTag, "Listener-onTracksChanged...")
            }

            override fun onLoadingChanged(isLoading: Boolean) {
//                Log.d(logTag, "Listener-onLoadingChanged...isLoading:$isLoading")
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                Log.d(logTag, "Listener-onPlayerStateChanged...$playbackState")
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
//                Log.d(logTag, "Listener-onRepeatModeChanged...")
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPlayerError(error: ExoPlaybackException) {
//                Log.d(logTag, "Listener-onPlayerError...")
                player?.let {
                    it.stop()
                    it.prepare(loopingSource)
                    it.playWhenReady = true
                }
            }

            override fun onPositionDiscontinuity(reason: Int) {}
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
//                Log.d(logTag, "Listener-onPlaybackParametersChanged...")
            }

            override fun onSeekProcessed() {}
        })
        player?.playWhenReady = true
        player?.setVideoDebugListener(this) //for listening to resolution change and  outputing the resolution

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

    override fun onVideoEnabled(counters: DecoderCounters) {
//        Log.d(logTag, "onVideoEnabled")
    }

    override fun onVideoDecoderInitialized(decoderName: String, initializedTimestampMs: Long, initializationDurationMs: Long) {
//        Log.d(logTag, "onVideoDecoderInitialized")
    }

    override fun onVideoInputFormatChanged(format: Format) {
//        Log.d(logTag, "onVideoInputFormatChanged")
    }

    override fun onDroppedFrames(count: Int, elapsedMs: Long) {
//        Log.d(logTag, "onDroppedFrames")
    }

    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
//        Log.d(logTag, "onVideoSizeChanged [ width: $width height: $height]")
    }

    override fun onRenderedFirstFrame(surface: Surface?) {
//        Log.d(logTag, "onRenderedFirstFrame")
    }

    override fun onVideoDisabled(counters: DecoderCounters) {
//        Log.d(logTag, "onVideoDisabled")
    }
}
