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
        //TODO
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
