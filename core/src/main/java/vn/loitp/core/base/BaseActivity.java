package vn.loitp.core.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.EventBusData;

//TODO change const debug

public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Activity activity;
    protected String TAG;
    private RelativeLayout rootView;

    private InterstitialAd interstitialAd;

    protected boolean isShowAdWhenExist = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = setActivity();
        TAG = setTag();
        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setCustomStatusBar(true);
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceId());

        interstitialAd = LUIUtil.createAdFull(activity);

        ScrollView scrollView = (ScrollView) activity.findViewById(R.id.scroll_view);
        if (scrollView != null) {
            LUIUtil.setPullLikeIOSVertical(scrollView);
        }
        rootView = (RelativeLayout) activity.findViewById(R.id.root_view);
    }

    protected void setCustomStatusBar(boolean shouldChangeStatusBarTintToDark) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (shouldChangeStatusBarTintToDark) {
                //white status bar with black icons
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.White));
                getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.Black));
            } else {
                //black status bar with white icons
                decor.setSystemUiVisibility(0);
                getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.Black));
                getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.Black));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //black status bar with white icons
            getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.Black));
            getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.Black));
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        }
    }

    @Override
    protected void onDestroy() {
        //AlertMessage.closeAll();
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        //TODO maybe in some cases we don't need to check internet connection
        /*if (!NetworkUtils.hasConnection(this)) {
            subscriber.onError(new NoConnectionException());
            return;
        }*/

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
    }

    protected abstract boolean setFullScreen();

    protected abstract String setTag();

    protected abstract Activity setActivity();

    protected abstract int setLayoutResourceId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LUIUtil.transActivityFadeIn(activity);
        if (isShowAdWhenExist) {
            LUIUtil.displayInterstitial(interstitialAd, 50);
        }
    }

    private TextView tvConnectStt;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.ConnectEvent event) {
        LLog.d(TAG, "onMessageEvent " + event.isConnected());
        if (!event.isConnected()) {
            if (rootView != null) {
                if (tvConnectStt == null) {
                    LLog.d(TAG, "tvConnectStt == null");
                    tvConnectStt = new TextView(activity);
                    tvConnectStt.setTextColor(Color.WHITE);
                    tvConnectStt.setBackgroundColor(Color.RED);
                    tvConnectStt.setPadding(20, 20, 20, 20);
                    tvConnectStt.setGravity(Gravity.CENTER);
                    tvConnectStt.setText("isConnected: " + event.isConnected());

                    RelativeLayout.LayoutParams rLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rLParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                    rootView.addView(tvConnectStt, rLParams);
                } else {
                    LLog.d(TAG, "tvConnectStt != null");
                    tvConnectStt.setText("isConnected: " + event.isConnected());
                }
            }
        } else {
            if (tvConnectStt != null) {
                tvConnectStt.setVisibility(View.GONE);
                tvConnectStt = null;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
