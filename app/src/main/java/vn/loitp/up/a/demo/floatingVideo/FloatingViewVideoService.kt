package vn.loitp.up.a.demo.floatingVideo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.R
import vn.loitp.up.a.cv.video.exo.mng.PlayerManager
import vn.loitp.up.a.sv.endless.log

class FloatingViewVideoService : Service() {

    private var mWindowManager: WindowManager? = null
    private lateinit var mFloatingView: View

    private var playerManager: PlayerManager? = null
    private var playerView: PlayerView? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    override fun onCreate() {
        super.onCreate()

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.l_demo_floating_video, null)
        setupViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
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
            PixelFormat.TRANSLUCENT
        )

        // Specify the view position
        params.gravity =
            Gravity.TOP or Gravity.START // Initially view will be added to top-left corner
        params.x = 0
        params.y = 100

        // Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)

        val ivClose = mFloatingView.findViewById<ImageView>(R.id.ivClose)
        val ivFull = mFloatingView.findViewById<ImageView>(R.id.ivFull)
        playerView = mFloatingView.findViewById(R.id.playerView)
        val rlMove = mFloatingView.findViewById<RelativeLayout>(R.id.rlMove)

        // Set the close button
        ivClose.setSafeOnClickListener {
            stopSelf()
        }

        playVideo()

        ivFull.setSafeOnClickListener {
            openApp()
        }
        // Drag and move floating view using user's touch action.
        rlMove.setOnTouchListener(object : OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {

                        // remember the initial position.
                        initialX = params.x
                        initialY = params.y

                        // get the touch location
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        val xDiff = (event.rawX - initialTouchX).toInt()
                        val yDiff = (event.rawY - initialTouchY).toInt()
                        log("xDiff $xDiff yDiff $yDiff")
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // Calculate the X and Y coordinates of the view.
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()

                        // Update the layout with new X & Y coordinate
                        mWindowManager?.updateViewLayout(mFloatingView, params)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onDestroy() {
        playerManager?.release()
        mWindowManager?.removeView(mFloatingView)
        super.onDestroy()
    }

    // Set the view while floating view is expanded.
    // Set the play button.
    // Open the application on thi button click
    private fun openApp() {
        // Open the application  click.
        val intent = Intent(this@FloatingViewVideoService, FloatingWidgetVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        // close the service and remove view from the view hierarchy
        stopSelf()
    }

    private fun playVideo() {
        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        val linkIMAAd = getString(com.loitp.R.string.ad_tag_url)

        playerManager = PlayerManager(context = this, urlIMAAd = linkIMAAd)
        playerView?.let { pv ->
            playerManager?.init(context = this, playerView = pv, linkPlay = linkPlay)
        }
    }
}
