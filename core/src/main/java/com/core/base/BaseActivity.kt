package com.core.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.R
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
    protected lateinit var activity: Activity
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

    protected abstract fun setFullScreen(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        activity = this

        val tmpLogTag = javaClass.getAnnotation(LogTag::class.java)
        logTag = "logTag$tmpLogTag"

        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            //LActivityUtil.hideSystemUI(getWindow().getDecorView());
        }
        setCustomStatusBar(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimary))
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
                        LConnectivityUtil.onNetworkConnectionChanged(context = activity, isConnected = true)
                    }

                    override fun onDisconnected() {
                        LConnectivityUtil.onNetworkConnectionChanged(context = activity, isConnected = false)
                    }

                    override fun getContext(): Context {
                        return this@BaseActivity
                    }
                })

        //autoanimation
        //SwitchAnimationUtil().startAnimation(window.decorView, SwitchAnimationUtil.AnimationType.SCALE)

        if (isShowAdWhenExit) {
            interstitialAd = LUIUtil.createAdFull(activity)
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
            //getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            //getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));

            window.statusBarColor = colorStatusBar
            window.navigationBarColor = colorNavigationBar
        }

        //set color for status bar
        //StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.Red));

        //add alpha to color
        //StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.Red), 50);

        //translucent status bar
        //StatusBarCompat.translucentStatusBar(activity);

        //should hide status bar background (default black background) when SDK >= 21
        //StatusBarCompat.translucentStatusBar(activity, true);

        //set color for CollapsingToolbarLayout
        //setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, int statusColor)
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
        val alertDialog = LDialogUtil.showDialog1(context = activity,
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
        LDialogUtil.showDialog1(context = activity,
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
            LActivityUtil.tranOut(activity)
        }
        if (isShowAdWhenExit && !Constants.IS_DEBUG) {
            interstitialAd?.let {
                LUIUtil.displayInterstitial(interstitial = it, maxNumber = 70)
            }
        } else {
            //dont use LLog here
            Log.d(logTag, "onBackPressed dont displayInterstitial because isShowAdWhenExit=$isShowAdWhenExit")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        onNetworkChange(event)
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {}

    protected fun showShort(msg: String?) {
        LToast.showShort(activity, msg, R.drawable.l_bkg_horizontal)
    }

    protected fun showLong(msg: String?) {
        LToast.showLong(activity, msg, R.drawable.l_bkg_horizontal)
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
