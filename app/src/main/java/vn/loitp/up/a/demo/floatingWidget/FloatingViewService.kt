package vn.loitp.up.a.demo.floatingWidget

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.loitp.views.toast.LToast
import vn.loitp.R

class FloatingViewService : Service() {
    private var mWindowManager: WindowManager? = null
    private lateinit var mFloatingView: View

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun onCreate() {
        super.onCreate()

        setupViews()
    }

    @SuppressLint("InflateParams")
    private fun setupViews() {
        mFloatingView =
            LayoutInflater.from(this).inflate(R.layout.l_demo_floating_widget, null)

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

        val rlRootContainer = mFloatingView.findViewById<View>(R.id.rlRootContainer)
        val rlCollapse = mFloatingView.findViewById<RelativeLayout>(R.id.rlCollapse)
        val llExpanded = mFloatingView.findViewById<LinearLayoutCompat>(R.id.llExpanded)
        val ivClose = mFloatingView.findViewById<ImageView>(R.id.ivClose)
        val ivPlay = mFloatingView.findViewById<ImageView>(R.id.ivPlay)
        val nextButton = mFloatingView.findViewById<ImageView>(R.id.ivNext)
        val ivPrev = mFloatingView.findViewById<ImageView>(R.id.ivPrev)
        val ivCloseButton = mFloatingView.findViewById<ImageView>(R.id.ivCloseButton)
        val ivOpenButton = mFloatingView.findViewById<ImageView>(R.id.ivOpenButton)

        ivClose?.setOnClickListener {
            // close the service and remove the from from the window
            stopSelf()
        }

        // Set the view while floating view is expanded.
        // Set the play button.

        ivPlay?.setOnClickListener {
            LToast.show("Playing the song.")
        }

        // Set the next button.
        nextButton?.setOnClickListener {
            LToast.show("Playing next song.")
        }

        // Set the pause button.
        ivPrev?.setOnClickListener {
            LToast.show("Playing previous song.")
        }

        // Set the close button
        ivCloseButton.setOnClickListener {
            rlCollapse.visibility = View.VISIBLE
            llExpanded.visibility = View.GONE
        }

        // Open the application on thi button click
        ivOpenButton.setOnClickListener {
            // Open the application  click.
            val intent = Intent(this@FloatingViewService, FloatingWidgetActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            // close the service and remove view from the view hierarchy
            stopSelf()
        }

        // Drag and move floating view using user's touch action.
        rlRootContainer.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            @SuppressLint("ClickableViewAccessibility")
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

                        // The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        // So that is click event.
                        if (xDiff < 10 && yDiff < 10) {
                            if (isViewCollapsed()) {
                                // When user clicks on the image view of the collapsed layout,
                                // visibility of the collapsed layout will be changed to "View.GONE"
                                // and expanded view will become visible.
                                rlCollapse.visibility = View.GONE
                                llExpanded.visibility = View.VISIBLE
                            }
                        }
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

    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */

    private fun isViewCollapsed(): Boolean {
        return mFloatingView.findViewById<View>(R.id.rlCollapse).visibility == View.VISIBLE
    }

    override fun onDestroy() {
        mWindowManager?.removeView(mFloatingView)
        super.onDestroy()
    }
}
