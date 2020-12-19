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
import com.BuildConfig
import com.R
import com.annotation.*
import com.core.common.Constants
import com.core.utilities.*
import com.data.EventBusData
import com.google.ads.interactivemedia.v3.internal.ff
import com.google.android.gms.ads.InterstitialAd
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper
import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener
import com.views.LToast
import com.views.bottomsheet.BottomSheetOptionFragment
import com.views.smoothtransition.SwitchAnimationUtil
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//animation https://github.com/dkmeteor/SmoothTransition
abstract class BaseActivity : AppCompatActivity() {
    protected var compositeDisposable = CompositeDisposable()
    protected var logTag: String? = null

    var delayMlsIdleTime: Long = 60 * 1000//60s
    private var handlerIdleTime: Handler? = null
    private var runnableIdleTime: Runnable? = null
    var isIdleTime = false

    private var interstitialAd: InterstitialAd? = null
    private var isShowAdWhenExit = false
    private var isShowAnimWhenExit = true

    protected abstract fun setLayoutResourceId(): Int

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

        val isDarkTheme = LUIUtil.isDarkTheme()
//        logD("onCreate isDarkTheme $isDarkTheme")
        val isSwipeActivity = javaClass.getAnnotation(IsSwipeActivity::class.java)?.value
                ?: false
//        logD("onCreate isSwipeActivity $isSwipeActivity")
        if (isSwipeActivity) {
            if (isDarkTheme) {
                setTheme(R.style.DarkSwipeTheme)
//                logD("onCreate setTheme DarkSwipeTheme")
            } else {
                setTheme(R.style.LightSwipeTheme)
//                logD("onCreate setTheme LightSwipeTheme")
            }
        } else {
            if (isDarkTheme) {
                setTheme(R.style.DarkTheme)
//                logD("onCreate setTheme DarkTheme")
            } else {
                setTheme(R.style.LightTheme)
//                logD("onCreate setTheme LightTheme")
            }
        }

        val isFullScreen = javaClass.getAnnotation(IsFullScreen::class.java)?.value ?: false
        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)

            //TODO loitpp revert if android R
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.insetsController?.hide(WindowInsets.Type.statusBars())
//            } else {
//                @Suppress("DEPRECATION")
//                window.setFlags(
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN
//                )
//            }

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setCustomStatusBar(colorStatusBar = LAppResource.getColor(R.color.colorPrimary), colorNavigationBar = LAppResource.getColor(R.color.colorPrimary))

        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val layoutId = setLayoutResourceId()
        if (layoutId != Constants.NOT_FOUND) {
            setContentView(setLayoutResourceId())
        }

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(object : OnNetworkConnectionChangeListener {
                    override fun onConnected() {
                        LConnectivityUtil.onNetworkConnectionChanged(isConnected = true)
                    }

                    override fun onDisconnected() {
                        LConnectivityUtil.onNetworkConnectionChanged(isConnected = false)
                    }

                    override fun getContext(): Context {
                        return this@BaseActivity
                    }
                })

        //auto animation
        val isAutoAnimation = javaClass.getAnnotation(IsAutoAnimation::class.java)?.value
                ?: false
        if (isAutoAnimation) {
            SwitchAnimationUtil().startAnimation(root = window.decorView, type = SwitchAnimationUtil.AnimationType.SCALE)
        }

        isShowAdWhenExit = javaClass.getAnnotation(IsShowAdWhenExit::class.java)?.value ?: false
        if (isShowAdWhenExit) {
            interstitialAd = LUIUtil.createAdFull(this)
            /*interstitialAd?.adListener = object : AdListener() {
                override fun onAdLoaded() {
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                }

                override fun onAdOpened() {
                }

                override fun onAdLeftApplication() {
                }

                override fun onAdClosed() {
                }
            }*/
        }
        isShowAnimWhenExit = javaClass.getAnnotation(IsShowAnimWhenExit::class.java)?.value ?: true
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

    protected fun showDialogError(errMsg: String?, runnable: Runnable? = null) {
        if (errMsg.isNullOrEmpty()) {
            return
        }
        val alertDialog = LDialogUtil.showDialog1(
                context = this,
                title = getString(R.string.warning),
                msg = errMsg,
                button1 = getString(R.string.confirm),
                onClickButton1 = {
                    runnable?.run()
                }
        )
        alertDialog.setCancelable(false)
    }

    protected fun showDialogMsg(errMsg: String, runnable: Runnable? = null) {
        LDialogUtil.showDialog1(
                context = this,
                title = getString(R.string.app_name),
                msg = errMsg,
                button1 = getString(R.string.confirm),
                onClickButton1 = {
                    runnable?.run()
                }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (isShowAnimWhenExit) {
            LActivityUtil.tranOut(this)
        }
        if (isShowAdWhenExit && !BuildConfig.DEBUG) {
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
        onNetworkChange(event = event)
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {}

    protected fun showShortInformation(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortInformation(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortWarning(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortWarning(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortError(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortError(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongInformation(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongInformation(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongWarning(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongWarning(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongError(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongError(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            LToast.showShortDebug(msg)
        }
    }

    protected fun showLongDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            LToast.showLongInformation(msg)
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

    fun showBottomSheetOptionFragment(
            isCancelableFragment: Boolean = true,
            isShowIvClose: Boolean = true,
            title: String,
            message: String,
            textButton1: String? = null,
            textButton2: String? = null,
            textButton3: String? = null,
            onClickButton1: ((Unit) -> Unit)? = null,
            onClickButton2: ((Unit) -> Unit)? = null,
            onClickButton3: ((Unit) -> Unit)? = null,
            onDismiss: ((Unit) -> Unit)? = null
    ) {
        val bottomSheetOptionFragment = BottomSheetOptionFragment(
                isCancelableFragment = isCancelableFragment,
                isShowIvClose = isShowIvClose,
                title = title,
                message = message,
                textButton1 = textButton1,
                textButton2 = textButton2,
                textButton3 = textButton3,
                onClickButton1 = onClickButton1,
                onClickButton2 = onClickButton2,
                onClickButton3 = onClickButton3,
                onDismiss = onDismiss
        )
        bottomSheetOptionFragment.show(supportFragmentManager, bottomSheetOptionFragment.tag)
    }
}
