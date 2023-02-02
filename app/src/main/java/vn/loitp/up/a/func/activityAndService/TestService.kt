package vn.loitp.up.a.func.activityAndService

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
import android.widget.TextView
import com.loitp.core.ext.setSafeOnClickListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.R
import vn.loitp.up.a.demo.floatingWidget.CommunicateMng

class TestService : Service() {
    private lateinit var mWindowManager: WindowManager
    private lateinit var mFloatingView: View
    private lateinit var textView: TextView

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate() {
        super.onCreate()

        setupViews()
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun setupViews() {
        EventBus.getDefault().register(this)
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.l_floating_test, null)

        // Add the view to the window.
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
        mWindowManager.addView(mFloatingView, params)

        textView = mFloatingView.findViewById(R.id.textView)
        val ivClose = mFloatingView.findViewById<ImageView>(R.id.ivClose)

        ivClose.setSafeOnClickListener {
            CommunicateMng.postFromService(CommunicateMng.MsgFromService(msg = "User clicks close button -> stop service"))
            // close the service and remove the from from the window
            stopSelf()
        }
        setDrag(params)
        textView.text = "This is floating view"
        mFloatingView.findViewById<View>(R.id.bt0).setOnClickListener {
            CommunicateMng.postFromService(CommunicateMng.MsgFromService(msg = "Send msg to activity ${System.currentTimeMillis()}"))
        }
    }

    private fun setDrag(params: WindowManager.LayoutParams) {
        // Drag and move floating view using user's touch action.
        mFloatingView.findViewById<View>(R.id.rlRootContainer)
            .setOnTouchListener(object : OnTouchListener {
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
                        MotionEvent.ACTION_UP ->
                            /*int Xdiff = (int) (event.getRawX() - initialTouchX);
                            int Ydiff = (int) (event.getRawY() - initialTouchY);*/
                            return true
                        MotionEvent.ACTION_MOVE -> {
                            // Calculate the X and Y coordinates of the view.
                            params.x = initialX + (event.rawX - initialTouchX).toInt()
                            params.y = initialY + (event.rawY - initialTouchY).toInt()

                            // Update the layout with new X & Y coordinate
                            mWindowManager.updateViewLayout(mFloatingView, params)
                            return true
                        }
                    }
                    return false
                }
            })
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        mWindowManager.removeView(mFloatingView)
        super.onDestroy()
    }

    // listen msg from activity
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: CommunicateMng.MsgFromActivity) {
        textView.text = msg.msg
    }
}
