package com.core.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.R
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.common.Constants
import com.core.utilities.*
import com.data.EventBusData
import com.google.android.gms.ads.InterstitialAd
import com.interfaces.Callback1
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper
import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener
import com.views.LToast
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//animation https://github.com/dkmeteor/SmoothTransition
abstract class BaseActivity : AppCompatActivity() {
    protected var compositeDisposable = CompositeDisposable()
    protected var logTag: String? = null

    protected var delayMlsIdleTime: Long = 60 * 1000//60s
    private var handlerIdleTime: Handler? = null
    private var runnableIdleTime: Runnable? = null
    protected var isIdleTime = false

    private var interstitialAd: InterstitialAd? = null
    protected var isShowAdWhenExit = false
    protected var isShowAnimWhenExit = true

    protected fun setTransparentStatusNavigationBar() {
        //https://stackoverflow.com/questions/29311078/android-completely-transparent-status-bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val tmpLogTag = javaClass.getAnnotation(LogTag::class.java)
        logTag = "logTag" + tmpLogTag?.value

        val isFullScreen = javaClass.getAnnotation(IsFullScreen::class.java) ?: false
        if (isFullScreen == true) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            //LActivityUtil.hideSystemUI(getWindow().getDecorView());
        }
        setCustomStatusBar(colorStatusBar = LAppResource.getColor(R.color.colorPrimary), colorNavigationBar = LAppResource.getColor(R.color.colorPrimary))

        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val layoutId = javaClass.getAnnotation(LayoutId::class.java)
        layoutId?.value?.let {
            setContentView(it)
        }
        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(object : OnNetworkConnectionChangeListener {
                    override fun onConnected() {
                        LConnectivityUtil.onNetworkConnectionChanged(context = this@BaseActivity, isConnected = true)
                    }

                    override fun onDisconnected() {
                        LConnectivityUtil.onNetworkConnectionChanged(context = this@BaseActivity, isConnected = false)
                    }

                    override fun getContext(): Context {
                        return this@BaseActivity
                    }
                })

        //autoanimation
        //SwitchAnimationUtil().startAnimation(window.decorView, SwitchAnimationUtil.AnimationType.SCALE)

        if (isShowAdWhenExit) {
            interstitialAd = LUIUtil.createAdFull(this)
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        stopIdleTimeHandler()//stop first and then start
        startIdleTimeHandler(delayMlsIdleTime)
    }

    open fun stopIdleTimeHandler() {
        handlerIdleTime?.let { h ->
            runnableIdleTime?.let { r ->
                h.removeCallbacks(r)
            }
        }
    }

    open fun onActivityUserIdleAfterTime(delayMlsIdleTime: Long, isIdleTime: Boolean) {
        logD("onActivityUserIdleAfterTime delayMlsIdleTime: $delayMlsIdleTime, isIdleTime: $isIdleTime")
    }

    open fun startIdleTimeHandler(delayMls: Long) {
        delayMlsIdleTime = delayMls
        handlerIdleTime = Handler()
        runnableIdleTime = Runnable {
            isIdleTime = true
            onActivityUserIdleAfterTime(delayMlsIdleTime, isIdleTime)
        }
        handlerIdleTime?.let { h ->
            runnableIdleTime?.let { r ->
                isIdleTime = false
                h.postDelayed(r, delayMls)
            }
        }
    }

    protected fun setCustomStatusBar(colorStatusBar: Int, colorNavigationBar: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = colorStatusBar
            window.navigationBarColor = colorNavigationBar
        }

        //set color for status bar
//        StatusBarCompat.setStatusBarColor(activity = this, statusColor = LAppResource.getColor(R.color.red))

        //add alpha to color
//        StatusBarCompat.setStatusBarColor(activity = this, statusColor = LAppResource.getColor(R.color.red), alpha = 50)

        //translucent status bar
//        StatusBarCompat.translucentStatusBar(activity = this)

        //should hide status bar background (default black background) when SDK >= 21
//        StatusBarCompat.translucentStatusBar(activity = this, hideStatusBarBackground = true)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        compositeDisposable.clear()
        LDialogUtil.clearAll()
        stopIdleTimeHandler()
        //AutoRefreshNetworkUtil.removeAllRegisterNetworkListener()
        super.onDestroy()
    }

    protected fun handleException(throwable: Throwable) {
        logE("handleException: $throwable")
        showDialogError("Error: $throwable")
    }

    protected fun showDialogError(errMsg: String) {
        val alertDialog = LDialogUtil.showDialog1(context = this,
                title = getString(R.string.warning),
                msg = errMsg,
                button1 = getString(R.string.confirm),
                callback1 = object : Callback1 {
                    override fun onClick1() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }

    protected fun showDialogMsg(errMsg: String, runnable: Runnable? = null) {
        LDialogUtil.showDialog1(context = this,
                title = getString(R.string.app_name),
                msg = errMsg,
                button1 = getString(R.string.confirm),
                callback1 = object : Callback1 {
                    override fun onClick1() {
                        runnable?.run()
                    }
                })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShowAnimWhenExit) {
            LActivityUtil.tranOut(this)
        }
        if (isShowAdWhenExit && !Constants.IS_DEBUG) {
            interstitialAd?.let {
                LUIUtil.displayInterstitial(interstitial = it, maxNumber = 70)
            }
        } else {
            //don't use LLog here
            Log.d(logTag, "onBackPressed dont displayInterstitial because isShowAdWhenExit=$isShowAdWhenExit")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        onNetworkChange(event)
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {}

    protected fun showShort(msg: String?) {
        LToast.showShort(context = this, msg = msg, backgroundRes = R.drawable.l_bkg_horizontal)
    }

    protected fun showLong(msg: String?) {
        LToast.showLong(context = this, msg = msg, backgroundRes = R.drawable.l_bkg_horizontal)
    }

    protected fun showShortDebug(msg: String?) {
        if (Constants.IS_DEBUG) {
            showShort(msg)
        }
    }

    protected fun showLongDebug(msg: String?) {
        if (Constants.IS_DEBUG) {
            showLong(msg)
        }
    }

    protected fun <T : ViewModel> getViewModel(className: Class<T>): T {
        return ViewModelProvider(this).get(className)
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(it, msg)
        }
    }
}
