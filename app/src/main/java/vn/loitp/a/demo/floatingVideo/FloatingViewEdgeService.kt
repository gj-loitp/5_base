package vn.loitp.a.demo.floatingVideo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.view.* // ktlint-disable no-wildcard-imports
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import android.widget.RelativeLayout
import com.loitp.core.utilities.LDeviceUtil
import com.loitp.core.utilities.LScreenUtil
import vn.loitp.R
import kotlin.math.abs

class FloatingViewEdgeService : Service() {

    private var mWindowManager: WindowManager? = null
    private var screenWidth = 0
    private var screenHeight = 0
    private var statusBarHeight = 0

    private lateinit var params: WindowManager.LayoutParams
    private lateinit var mFloatingView: View
    private lateinit var rlMove: RelativeLayout
    private lateinit var viewBkgDestroy: View

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun onCreate() {
        super.onCreate()

        screenWidth = LScreenUtil.screenWidth
        screenHeight = LScreenUtil.screenHeight
        statusBarHeight = LScreenUtil.getStatusBarHeight()

        setupViews()

        // Drag and move floating view using user's touch action.
        dragAndMove()
    }

    @SuppressLint("InflateParams")
    private fun setupViews() {
        // Inflate the floating view layout we created
        mFloatingView =
            LayoutInflater.from(this).inflate(R.layout.layout_demo_floating_view_edge, null)
        rlMove = mFloatingView.findViewById(R.id.rlMove)
        viewBkgDestroy = mFloatingView.findViewById(R.id.viewBkgDestroy)

        // Add the view to the window.
        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )

        setSizeMoveView(isFirstSizeInit = true, isLarger = false)

        // Specify the view position
        params.apply {
            this.gravity =
                Gravity.TOP or Gravity.START // Initially view will be added to top-left corner
            this.x = screenWidth - moveViewWidth
            this.y =
                screenHeight - moveViewHeight - statusBarHeight // dell hieu sao phai tru getBottomBarHeight thi moi dung position :(
        }

        // Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)
    }

    // only update 1 one time
    private var isUpdatedUIVideoSize = false

    @Suppress("unused")
    private fun updateUIVideoSizeOneTime(
        videoW: Int,
        videoH: Int
    ) {
        if (!isUpdatedUIVideoSize) {
            val vW = screenWidth / 2
            val vH = vW * videoH / videoW
            val newPosX = params.x
            val newPosY =
                screenHeight - vH - statusBarHeight // dell hieu sao phai tru getBottomBarHeight thi moi dung position :(
            updateUISlide(x = newPosX, y = newPosY)
            isUpdatedUIVideoSize = true
        }
    }

    private var countDownTimer: CountDownTimer? = null
    private fun slideToPosition(
        goToPosX: Int,
        goToPosY: Int
    ) {
        val currentPosX = params.x
        val currentPosY = params.y

        val a = abs(goToPosX - currentPosX)
        val b = abs(goToPosY - currentPosY)

        countDownTimer = object : CountDownTimer(300, 3) {
            override fun onTick(t: Long) {
                val step = (300 - t) / 3.toFloat()
                val tmpX: Int
                val tmpY: Int
                if (currentPosX > goToPosX) {
                    if (currentPosY > goToPosY) {
                        tmpX = currentPosX - (a * step / 100).toInt()
                        tmpY = currentPosY - (b * step / 100).toInt()
                    } else {
                        tmpX = currentPosX - (a * step / 100).toInt()
                        tmpY = currentPosY + (b * step / 100).toInt()
                    }
                } else {
                    if (currentPosY > goToPosY) {
                        tmpX = currentPosX + (a * step / 100).toInt()
                        tmpY = currentPosY - (b * step / 100).toInt()
                    } else {
                        tmpX = currentPosX + (a * step / 100).toInt()
                        tmpY = currentPosY + (b * step / 100).toInt()
                    }
                }
                updateUISlide(tmpX, tmpY)
            }

            override fun onFinish() {
                updateUISlide(goToPosX, goToPosY)
            }
        }.start()
    }

    private fun updateUISlide(x: Int, y: Int) {
        params.x = x
        params.y = y
        mWindowManager?.updateViewLayout(mFloatingView, params)
    }

    private var mTapDetector: GestureDetector? = null

    private inner class GestureTap : SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            openApp()
            return true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dragAndMove() {
        mTapDetector = GestureDetector(baseContext, GestureTap())

        rlMove.setOnTouchListener(object : OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                mTapDetector?.onTouchEvent(event)
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
                        onMoveUp()
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // Calculate the X and Y coordinates of the view.
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()

                        // Update the layout with new X & Y coordinate
                        mWindowManager?.updateViewLayout(mFloatingView, params)
                        getLocationOnScreen(mFloatingView)
                        return true
                    }
                }
                return false
            }
        })
    }

    private enum class POS {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER_LEFT, CENTER_RIGHT, CENTER_TOP, CENTER_BOTTOM, LEFT, RIGHT, TOP, BOTTOM, CENTER
    }

    private var pos: POS? = null
    private fun notiPos(tmpPos: POS) {
        if (pos != tmpPos) {
            pos = tmpPos
            when (pos) {
                POS.TOP_LEFT, POS.TOP_RIGHT, POS.BOTTOM_LEFT, POS.BOTTOM_RIGHT -> {
                    LDeviceUtil.vibrate()
                    viewBkgDestroy.visibility = View.VISIBLE
                }
                else -> if (viewBkgDestroy.visibility != View.GONE) {
                    viewBkgDestroy.visibility = View.GONE
                }
            }
        }
    }

    private fun getLocationOnScreen(view: View) {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val posLeft = location[0]
        val posTop = location[1]
        val posRight = posLeft + view.width
        val posBottom = posTop + view.height
        val centerX = (posLeft + posRight) / 2
        val centerY = (posTop + posBottom) / 2
        if (centerX < 0) {
            when {
                centerY < 0 -> {
                    notiPos(POS.TOP_LEFT)
                }
                centerY > screenHeight -> {
                    notiPos(POS.BOTTOM_LEFT)
                }
                else -> {
                    notiPos(POS.CENTER_LEFT)
                }
            }
        } else if (centerX > screenWidth) {
            when {
                centerY < 0 -> {
                    notiPos(POS.TOP_RIGHT)
                }
                centerY > screenHeight -> {
                    notiPos(POS.BOTTOM_RIGHT)
                }
                else -> {
                    notiPos(POS.CENTER_RIGHT)
                }
            }
        } else {
            when {
                centerY < 0 -> {
                    notiPos(POS.CENTER_TOP)
                }
                centerY > screenHeight -> {
                    notiPos(POS.CENTER_BOTTOM)
                }
                else -> {
                    if (posLeft < 0) {
                        notiPos(POS.LEFT)
                    } else if (posRight > screenWidth) {
                        notiPos(POS.RIGHT)
                    } else {
                        when {
                            posTop < 0 -> {
                                notiPos(POS.TOP)
                            }
                            posBottom > screenHeight -> {
                                notiPos(POS.BOTTOM)
                            }
                            else -> {
                                notiPos(POS.CENTER)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onMoveUp() {
        if (pos == null) {
            return
        }
        val posX: Int
        val posY: Int
        val centerPosX: Int
        val centerPosY: Int
        when (pos) {
            POS.TOP_LEFT, POS.TOP_RIGHT, POS.BOTTOM_LEFT, POS.BOTTOM_RIGHT -> stopSelf()
            POS.TOP, POS.CENTER_TOP -> {
                posX = params.x
                centerPosX = posX + moveViewWidth / 2
                if (centerPosX < screenWidth / 2) {
                    slideToPosition(goToPosX = 0, goToPosY = 0)
                } else {
                    slideToPosition(goToPosX = screenWidth - moveViewWidth, goToPosY = 0)
                }
            }
            POS.BOTTOM, POS.CENTER_BOTTOM -> {
                posX = params.x
                centerPosX = posX + moveViewWidth / 2
                if (centerPosX < screenWidth / 2) {
                    slideToPosition(
                        goToPosX = 0,
                        goToPosY = screenHeight - moveViewHeight - statusBarHeight
                    )
                } else {
                    slideToPosition(
                        goToPosX = screenWidth - moveViewWidth,
                        goToPosY = screenHeight - moveViewHeight - statusBarHeight
                    )
                }
            }
            POS.LEFT, POS.CENTER_LEFT -> {
                posY = params.y
                centerPosY = posY + moveViewHeight / 2
                if (centerPosY < screenHeight / 2) {
                    slideToPosition(goToPosX = 0, goToPosY = 0)
                } else {
                    slideToPosition(
                        goToPosX = 0,
                        goToPosY = screenHeight - moveViewHeight - statusBarHeight
                    )
                }
            }
            POS.RIGHT, POS.CENTER_RIGHT -> {
                posY = params.y
                centerPosY = posY + moveViewHeight / 2
                if (centerPosY < screenHeight / 2) {
                    slideToPosition(goToPosX = screenWidth - moveViewWidth, goToPosY = 0)
                } else {
                    slideToPosition(
                        goToPosX = screenWidth - moveViewWidth,
                        goToPosY = screenHeight - moveViewHeight - statusBarHeight
                    )
                }
            }
            POS.CENTER -> {
                posX = params.x
                posY = params.y
                centerPosX = posX + moveViewWidth / 2
                centerPosY = posY + moveViewHeight / 2
                if (centerPosX < screenWidth / 2) {
                    if (centerPosY < screenHeight / 2) {
                        slideToPosition(goToPosX = 0, goToPosY = 0)
                    } else {
                        slideToPosition(
                            goToPosX = 0,
                            goToPosY = screenHeight - moveViewHeight - statusBarHeight
                        )
                    }
                } else {
                    if (centerPosY < screenHeight / 2) {
                        slideToPosition(goToPosX = screenWidth - moveViewWidth, goToPosY = 0)
                    } else {
                        slideToPosition(
                            goToPosX = screenWidth - moveViewWidth,
                            goToPosY = screenHeight - moveViewHeight - statusBarHeight
                        )
                    }
                }
            }
            else -> {}
        }
    }

    override fun onDestroy() {
        mWindowManager?.removeView(mFloatingView)
        countDownTimer?.cancel()
        super.onDestroy()
    }

    private fun openApp() {
        // Open the application  click.
        val intent = Intent(this@FloatingViewEdgeService, FloatingWidgetVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        // close the service and remove view from the view hierarchy
        stopSelf()
    }

    // click vo se larger, click lan nua de smaller
    @Suppress("unused")
    private fun setSizeMoveView(
        isFirstSizeInit: Boolean,
        isLarger: Boolean
    ) {
        var w = 0
        var h = 0
        if (isFirstSizeInit) {
            w = screenWidth / 2
            h = w * 9 / 16
        } else {
            // works fine
            // OPTION 1: isLarger->mini player se to hon 1 chut
            // !isLarger->ve trang thai ban dau
            /*if (isLarger) {
                w = getMoveViewWidth() * 120 / 100;
                h = getMoveViewHeight() * 120 / 100;
            } else {
                int videoW = fuzVideo.getVideoW();
                int videoH = fuzVideo.getVideoH();
                w = screenWidth / 2;
                h = w * videoH / videoW;
            }*/
        }
        if (w != 0 && h != 0) {
            rlMove.layoutParams.width = w
            rlMove.layoutParams.height = h
            rlMove.requestLayout()
        }
    }

    private val moveViewWidth: Int
        get() = rlMove.layoutParams.width

    private val moveViewHeight: Int
        get() = rlMove.layoutParams.height
}
