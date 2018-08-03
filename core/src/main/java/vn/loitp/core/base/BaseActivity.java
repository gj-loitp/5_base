package vn.loitp.core.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.android.gms.ads.InterstitialAd;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import loitp.core.R;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.EventBusData;
import vn.loitp.restapi.livestar.corev3.api.exception.NoConnectionException;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;

//animation https://github.com/dkmeteor/SmoothTransition
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Activity activity;
    protected String TAG;
    private RelativeLayout rootView;

    private InterstitialAd interstitialAd;

    protected boolean isShowAdWhenExist = true;

    protected RelativeLayout getRootView() {
        return rootView;
    }

    protected void setRootViewPadding() {
        rootView.setPadding(0, DisplayUtil.getStatusHeight(activity), 0, DisplayUtil.getNavigationBarHeight(activity));
    }

    protected void setTransparentStatusNavigationBar() {
        //https://stackoverflow.com/questions/29311078/android-completely-transparent-status-bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = this;
        TAG = setTag();
        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //LActivityUtil.hideSystemUI(getWindow().getDecorView());
        } else {
            setCustomStatusBar(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimary));
        }

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(setLayoutResourceId());
        //new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), SwitchAnimationUtil.AnimationType.SCALE);
        interstitialAd = LUIUtil.createAdFull(activity);
        View view = activity.findViewById(R.id.scroll_view);
        if (view != null) {
            if (view instanceof ScrollView) {
                LUIUtil.setPullLikeIOSVertical((ScrollView) view);
            } else if (view instanceof NestedScrollView) {
                LUIUtil.setPullLikeIOSVertical((NestedScrollView) view);
            }
        }
        rootView = (RelativeLayout) activity.findViewById(R.id.root_view);
        if (rootView == null) {
            throw new NullPointerException("Please set top root layout is relative layout, and set id root_view");
        }
    }

    protected void setCustomStatusBar(int colorStatusBar, int colorNavigationBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            //getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));

            getWindow().setStatusBarColor(colorStatusBar);
            getWindow().setNavigationBarColor(colorNavigationBar);
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

    @Override
    protected void onDestroy() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        LDialogUtil.clearAll();
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        if (!LConnectivityUtil.isConnected(activity)) {
            subscriber.onError(new NoConnectionException());
            return;
        }

        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        compositeSubscription.add(subscription);
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void handleException(Throwable throwable) {
        LLog.e("handleException", throwable.toString());
        if (throwable != null) {
            showDialogError("Error: " + throwable.toString());
        }
    }

    protected void showDialogError(String errMsg) {
        AlertDialog alertDialog = LDialogUtil.showDialog1(activity, getString(R.string.warning), errMsg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }

    protected void showDialogMsg(String errMsg) {
        LDialogUtil.showDialog1(activity, getString(R.string.app_name), errMsg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                //do nothing
            }
        });
    }

    protected abstract boolean setFullScreen();

    protected abstract String setTag();

    protected abstract int setLayoutResourceId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LActivityUtil.tranOut(activity);
        if (isShowAdWhenExist && !Constants.IS_DEBUG) {
            LUIUtil.displayInterstitial(interstitialAd, 60);
        } else {
            LLog.d(TAG, "onBackPressed dont displayInterstitial because isShowAdWhenExist=" + isShowAdWhenExist);
        }
    }

    private TextView tvConnectStt;

    private void showTvNoConnect() {
        if (rootView != null) {
            if (tvConnectStt == null) {
                //LLog.d(TAG, "tvConnectStt == null -> new tvConnectStt");
                tvConnectStt = new TextView(activity);
                tvConnectStt.setTextColor(Color.WHITE);
                tvConnectStt.setBackgroundColor(ContextCompat.getColor(activity, R.color.Red));
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
    }

    private void hideTvNoConnect() {
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.ConnectEvent event) {
        //TAG = "onMessageEvent";
        //LLog.d(TAG, "onMessageEvent " + event.isConnected());
        onNetworkChange(event);
        if (!event.isConnected()) {
            //no network
            showTvNoConnect();
        } else {
            hideTvNoConnect();
        }
    }

    protected void onNetworkChange(EventBusData.ConnectEvent event) {

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (!LConnectivityUtil.isConnected(activity)) {
            showTvNoConnect();
        }
        super.onResume();
    }
}
