package com.loitpcore.views.lDebugView

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.* // ktlint-disable no-wildcard-imports
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.loitpcore.R
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LDateUtil
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LDebugViewService : Service(), OnTouchListener {

    private var mWindowManager: WindowManager? = null
    private var mFloatingView: View? = null
    private var params: WindowManager.LayoutParams? = null
    private var collapsedView: View? = null
    private var expandedView: View? = null
    private var llRootTv: LinearLayout? = null
    private var scrollView: ScrollView? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_debug_view_service, null)

        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Specify the view position
        params?.let {
            it.gravity =
                Gravity.TOP or Gravity.START // Initially view will be added to top-left corner
            it.x = 0
            it.y = 100
        }

        // Add the view to the window
        mWindowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)

        collapsedView = mFloatingView?.findViewById(R.id.rlCollapse)
        expandedView = mFloatingView?.findViewById(R.id.llExpanded)

        expandedView?.apply {
            layoutParams.width = LScreenUtil.screenWidth / 2
            layoutParams.height = LScreenUtil.screenHeight * 2 / 3
            requestLayout()
        }

        llRootTv = mFloatingView?.findViewById(R.id.ll_root_tv)
        scrollView = mFloatingView?.findViewById(R.id.scrollView)

        // Set the close button
        val closeButtonCollapsed = mFloatingView?.findViewById<ImageView>(R.id.ivClose)
        closeButtonCollapsed?.setOnClickListener {
            // close the service and remove the from from the window
            stopSelf()
        }

        // Set the close button
        val closeButton = mFloatingView?.findViewById<ImageView>(R.id.ivCloseButton)
        closeButton?.setOnClickListener {
            collapsedView?.visibility = View.VISIBLE
            expandedView?.visibility = View.GONE
        }

        // Drag and move floating view using user's touch action.
        mFloatingView?.findViewById<View>(R.id.rlRootContainer)?.setOnTouchListener(this)
    }

    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */
    private val isViewCollapsed: Boolean
        get() = mFloatingView == null || mFloatingView?.findViewById<View>(R.id.rlCollapse)?.visibility == View.VISIBLE
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.rlRootContainer) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    // remember the initial position.

                    params?.let {
                        initialX = it.x
                        initialY = it.y

                        // get the touch location
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }

                    return true
                }
                MotionEvent.ACTION_UP -> {
                    val xDiff = (event.rawX - initialTouchX).toInt()
                    val yDiff = (event.rawY - initialTouchY).toInt()

                    // The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                    // So that is click event.
                    if (xDiff < 10 && yDiff < 10) {
                        if (isViewCollapsed) {
                            // When user clicks on the image view of the collapsed layout,
                            // visibility of the collapsed layout will be changed to "View.GONE"
                            // and expanded view will become visible.
                            collapsedView?.visibility = View.GONE
                            expandedView?.visibility = View.VISIBLE
                        }
                    }
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Calculate the X and Y coordinates of the view.
                    params?.x = initialX + (event.rawX - initialTouchX).toInt()
                    params?.y = initialY + (event.rawY - initialTouchY).toInt()

                    // Update the layout with new X & Y coordinate
                    mWindowManager?.updateViewLayout(mFloatingView, params)
                    return true
                }
            }
            return false
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun print(msgFromActivity: LComunicateDebug.MsgFromActivity?) {
        if (msgFromActivity == null) {
            return
        }
        val currentTime =
            LDateUtil.getDateCurrentTimeZoneMls(System.currentTimeMillis(), "HH:mm:ss")
        val textView = TextView(this)
        LUIUtil.setTextSize(textView, LAppResource.getDimenValue(R.dimen.txt_medium).toFloat())
        if (msgFromActivity.any == null) {
            textView.text = currentTime + " : " + msgFromActivity.msg
        } else {
            LUIUtil.printBeautyJson(o = msgFromActivity.any, textView = textView, tag = currentTime)
        }
        LUIUtil.setTextSize(
            textView = textView,
            size = baseContext.resources.getDimension(R.dimen.txt_tiny)
        )
        when (msgFromActivity.type) {
            LComunicateDebug.MsgFromActivity.TYPE_D -> {
                textView.setTextColor(Color.WHITE)
            }
            LComunicateDebug.MsgFromActivity.TYPE_E -> {
                textView.setTextColor(Color.RED)
            }
            LComunicateDebug.MsgFromActivity.TYPE_I -> {
                textView.setTextColor(Color.GREEN)
            }
        }
        llRootTv?.addView(textView)
        scrollView?.let { sv ->
            sv.post {
                sv.fullScroll(View.FOCUS_DOWN)
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        mWindowManager?.removeView(mFloatingView)
        super.onDestroy()
    }

    // listen msg from activity
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: LComunicateDebug.MsgFromActivity?) {
        print(msgFromActivity = msg)
    }
}
