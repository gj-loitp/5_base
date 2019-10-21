package com.core.base

import android.app.Activity
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.R
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.core.utilities.connection.LConectifyService
import com.data.EventBusData
import com.google.android.gms.ads.InterstitialAd
import com.views.LToast
import com.views.layout.floatdraglayout.DisplayUtil
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

    protected var rootView: RelativeLayout? = null
        private set
    private var interstitialAd: InterstitialAd? = null
    protected var isShowAdWhenExit = false
    protected var isShowAnimWhenExit = true

    protected fun setRootViewPadding() {
        rootView?.setPadding(0, DisplayUtil.getStatusHeight(activity), 0, DisplayUtil.getNavigationBarHeight(activity))
    }

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

        //autoanimation
        //new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);

        interstitialAd = LUIUtil.createAdFull(activity)

        val view = activity.findViewById<View>(R.id.scroll_view)
        view?.let {
            if (view is ScrollView) {
                LUIUtil.setPullLikeIOSVertical(view)
            } else if (view is NestedScrollView) {
                LUIUtil.setPullLikeIOSVertical(view);
            }
        }
        try {
            rootView = activity.findViewById(R.id.root_view)
        } catch (e: ClassCastException) {
            Log.e(TAG, "ClassCastException $e")
        }
        scheduleJob()
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
        LLog.d(TAG, "startIdleTimeHandler delayMls: $delayMls")
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
        super.onDestroy()
    }

    fun startActivity(clazz: Class<out Activity>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
        LActivityUtil.tranIn(activity)
    }

    protected fun handleException(throwable: Throwable) {
        LLog.e("handleException", throwable.toString())
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

    protected fun showDialogMsg(errMsg: String) {
        LDialogUtil.showDialog1(activity, getString(R.string.app_name), errMsg, getString(R.string.confirm), object : LDialogUtil.Callback1 {
            override fun onClick1() {
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
            Log.d("interstitial", "onBackPressed dont displayInterstitial because isShowAdWhenExit=$isShowAdWhenExit")
        }
    }

    //private TextView tvConnectStt;

    /*private void showTvNoConnect() {
        if (rootView != null && isShowTvConnectStt) {
            if (tvConnectStt == null) {
                //LLog.d(TAG, "tvConnectStt == null -> new tvConnectStt");
                tvConnectStt = new TextView(activity);
                tvConnectStt.setTextColor(Color.WHITE);
                tvConnectStt.setBackgroundColor(ContextCompat.getColor(activity, R.color.RedTrans));
                tvConnectStt.setPadding(20, 20, 20, 20);
                tvConnectStt.setGravity(Gravity.CENTER);
                //tvConnectStt.setText(R.string.check_ur_connection);
                tvConnectStt.setText(R.string.check_ur_connection);
                LUIUtil.setTextShadow(tvConnectStt);
                LUIUtil.setTextSize(tvConnectStt, TypedValue.COMPLEX_UNIT_DIP, 10);

                RelativeLayout.LayoutParams rLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rLParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
                rootView.addView(tvConnectStt, rLParams);
                //rootView.requestLayout();

                tvConnectStt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideTvNoConnect();
                    }
                });
            } else {
                //LLog.d(TAG, "tvConnectStt != null");
                tvConnectStt.setText(R.string.check_ur_connection);
            }
            LAnimationUtil.play(tvConnectStt, Techniques.FadeIn);
        } else {
            //LLog.d(TAG, "rootView == null");
        }
    }*/

    /*protected void goneTvNoConnect() {
        if (tvConnectStt != null) {
            tvConnectStt.setVisibility(View.GONE);
        }
    }*/

    /*private void hideTvNoConnect() {
        if (tvConnectStt != null) {
            LAnimationUtil.play(tvConnectStt, Techniques.FadeOut, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    if (tvConnectStt != null) {
                        tvConnectStt.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onRepeat() {
                    //do nothing
                }

                @Override
                public void onStart() {
                    //do nothing
                }
            });
            tvConnectStt = null;
        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        //TAG = "onMessageEvent";
        //LLog.d(TAG, "onMessageEvent " + event.isConnected());
        onNetworkChange(event)
        /*if (!event.isConnected()) {
            //no network
            showTvNoConnect();
        } else {
            hideTvNoConnect();
        }*/
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {}

    public override fun onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val startServiceIntent = Intent(this, LConectifyService::class.java)
            startService(startServiceIntent)
        }
        super.onStart()
    }

    public override fun onStop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stopService(Intent(this, LConectifyService::class.java))
        }
        super.onStop()
    }

    /*@Override
    protected void onResume() {
        if (!LConnectivityUtil.isConnected(activity)) {
            showTvNoConnect();
        }
        super.onResume();
    }*/


    private fun scheduleJob() {
        val myJob: JobInfo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myJob = JobInfo.Builder(0, ComponentName(this, LConectifyService::class.java))
                    .setRequiresCharging(true)
                    .setMinimumLatency(1000)
                    .setOverrideDeadline(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build()
            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(myJob)
        }
    }

    protected fun showShort(msg: String) {
        LToast.showShort(activity, msg, R.drawable.l_bkg_horizontal)
    }

    protected fun showLong(msg: String) {
        LToast.showLong(activity, msg, R.drawable.l_bkg_horizontal)
    }
}
