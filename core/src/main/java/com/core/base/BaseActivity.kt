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
import com.core.common.Constants
import com.core.utilities.*
import com.data.EventBusData
import com.google.android.gms.ads.InterstitialAd
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
    protected lateinit var TAG: String
    //protected boolean isShowTvConnectStt = false;

    protected var delayMlsIdleTime: Long = 60 * 1000//60s
    private var handlerIdleTime: Handler? = null
    private var runnableIdleTime: Runnable? = null
    protected var isIdleTime = false

    //protected var rootView: RelativeLayout? = null
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
        activity = this
        TAG = "TAG" + setTag()
        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            //LActivityUtil.hideSystemUI(getWindow().getDecorView());
        }
        setCustomStatusBar(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimary))

        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (setLayoutResourceId() != 0) {
            setContentView(setLayoutResourceId())
        }

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(object : OnNetworkConnectionChangeListener {
                    override fun onConnected() {
                        //LLog.d(TAG, "OnNetworkConnectionChangeListener onConnected")
                        LConnectivityUtil.onNetworkConnectionChanged(context = activity, isConnected = true)
                    }

                    override fun onDisconnected() {
                        //LLog.d(TAG, "OnNetworkConnectionChangeListener onDisconnected")
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

//        val view = activity.findViewById<View>(R.id.scroll_view)
//        view?.let { v ->
//            if (v is ScrollView) {
//                LUIUtil.setPullLikeIOSVertical(v)
//            } else if (v is NestedScrollView) {
//                LUIUtil.setPullLikeIOSVertical(v)
//            }
//        }

//        try {
//            rootView = activity.findViewById(R.id.root_view)
//        } catch (e: ClassCastException) {
//            Log.e(TAG, "ClassCastException $e")
//        }
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
        LLog.d(TAG, "onActivityUserIdleAfterTime delayMlsIdleTime: $delayMlsIdleTime, isIdleTime: $isIdleTime")
    }

    open fun startIdleTimeHandler(delayMls: Long) {
        //LLog.d(TAG, "startIdleTimeHandler delayMls: $delayMls")
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
        LLog.e(TAG, "handleException: $throwable")
        showDialogError("Error: $throwable")
    }

    protected fun showDialogError(errMsg: String) {
        val alertDialog = LDialogUtil.showDialog1(activity, getString(R.string.warning), errMsg, getString(R.string.confirm),
                object : LDialogUtil.Callback1 {
                    override fun onClick1() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }

    protected fun showDialogMsg(errMsg: String, runnable: Runnable? = null) {
        LDialogUtil.showDialog1(activity, getString(R.string.app_name), errMsg, getString(R.string.confirm), object : LDialogUtil.Callback1 {
            override fun onClick1() {
                runnable?.run()
            }
        })
    }

    protected abstract fun setFullScreen(): Boolean

    protected abstract fun setTag(): String?

    protected abstract fun setLayoutResourceId(): Int

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShowAnimWhenExit) {
            LActivityUtil.tranOut(activity)
        }
        if (isShowAdWhenExit && !Constants.IS_DEBUG) {
            interstitialAd?.let {
                LUIUtil.displayInterstitial(it, 70)
            }
        } else {
            //dont use LLog here
            Log.d(TAG, "onBackPressed dont displayInterstitial because isShowAdWhenExit=$isShowAdWhenExit")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        //TAG = "onMessageEvent"
        //LLog.d(TAG, "onMessageEvent isConnected: " + event.isConnected)
        onNetworkChange(event)
        /*if (event.isConnected) {
            hideTvNoConnect()
        } else {
            showTvNoConnect()
        }*/
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
}
