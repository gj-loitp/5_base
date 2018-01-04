package vn.loitp.core.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
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
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.ActivityData;
import vn.loitp.data.EventBusData;

//TODO change const debug

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = setActivity();
        TAG = setTag();
        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //setCustomStatusBar(true);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        setCustomStatusBar(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimary));
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceId());

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
    }

    protected void setCustomStatusBar(int colorStatusBar, int colorNavigationBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            //getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));

            getWindow().setStatusBarColor(colorStatusBar);
            getWindow().setNavigationBarColor(colorNavigationBar);
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
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
        showDialogError(throwable.getMessage());
    }

    protected void showDialogError(String errMsg) {
        LDialogUtil.showDialog1(activity, getString(R.string.warning), errMsg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                onBackPressed();
            }
        });
    }

    protected abstract boolean setFullScreen();

    protected abstract String setTag();

    protected abstract Activity setActivity();

    protected abstract int setLayoutResourceId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //LUIUtil.transActivityFadeIn(activity);
        int typeActivityTransition = ActivityData.getInstance().getType();
        if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT) {
            //do nothing
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT) {
            LActivityUtil.slideRight(activity);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT) {
            LActivityUtil.slideLeft(activity);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN) {
            LActivityUtil.slideUp(activity);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP) {
            LActivityUtil.slideDown(activity);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_FADE) {
            LActivityUtil.fade(activity);
        }
        if (isShowAdWhenExist) {
            LUIUtil.displayInterstitial(interstitialAd, 50);
        }

    }

    private TextView tvConnectStt;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.ConnectEvent event) {
        //LLog.d(TAG, "onMessageEvent " + event.isConnected());
        if (!event.isConnected()) {
            if (rootView != null) {
                if (tvConnectStt == null) {
                    //LLog.d(TAG, "tvConnectStt == null");
                    tvConnectStt = new TextView(activity);
                    tvConnectStt.setTextColor(Color.WHITE);
                    tvConnectStt.setBackgroundColor(Color.RED);
                    tvConnectStt.setPadding(20, 20, 20, 20);
                    tvConnectStt.setGravity(Gravity.CENTER);
                    tvConnectStt.setText(R.string.check_ur_connection);

                    RelativeLayout.LayoutParams rLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rLParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                    rootView.addView(tvConnectStt, rLParams);
                } else {
                    //LLog.d(TAG, "tvConnectStt != null");
                    tvConnectStt.setText(R.string.check_ur_connection);
                }
                LAnimationUtil.play(tvConnectStt, Techniques.FadeIn);
            }
        } else {
            if (tvConnectStt != null) {
                LAnimationUtil.play(tvConnectStt, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        tvConnectStt.setVisibility(View.GONE);
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
