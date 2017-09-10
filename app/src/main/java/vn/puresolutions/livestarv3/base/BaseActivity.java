package vn.puresolutions.livestarv3.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.FacebookSignInActivity;
import vn.puresolutions.livestar.activity.ForgotPasswordActivity;
import vn.puresolutions.livestar.activity.SignUpActivity;
import vn.puresolutions.livestar.core.api.exception.NoConnectionException;
import vn.puresolutions.livestar.core.api.model.ErrorResponse;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.helper.TrackingHelper;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.login.RegisterActivity;
import vn.puresolutions.livestarv3.activity.userprofile.EditInfoChangePassActivity;
import vn.puresolutions.livestarv3.utilities.old.NetworkUtils;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

//TODO remove scrollbar
//TODO change const debug
//TODO rafactor code, remove code v1
//TODO add ripple

public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Activity activity;
    protected String TAG;

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
        TrackingHelper.trackScreenName(getClass());
    }

    protected void setCustomStatusBar(boolean shouldChangeStatusBarTintToDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        //TODO maybe in some cases we don't need to check internet connection
        if (!NetworkUtils.hasConnection(this)) {
            subscriber.onError(new NoConnectionException());
            return;
        }

        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        compositeSubscription.add(subscription);
    }

    public void forceSignIn() {

    }

    public void startActivityAndFinish(Class<? extends Activity> clazz) {
        startActivity(clazz);
        finish();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void handleException(Throwable throwable) {
        if (throwable instanceof NoConnectionException) {
            AlertMessage.showError(this, R.string.cant_connect_to_internet);
        } else if (throwable instanceof SocketTimeoutException) {
            AlertMessage.showError(this, R.string.connect_timeout);
        } else if (throwable instanceof HttpException) {
            int statusCode = ((HttpException) throwable).code();
            if (statusCode == 204) {
                // isn't error
            } else if (this instanceof LoginActivity) {
                switch (statusCode) {
                    case 500:
                        AlertMessage.showError(this, "SĐT hoặc mật khẩu không đúng \n Vui lòng kiểm tra lại");
                        break;
                    default:
                        AlertMessage.showError(this, R.string.an_error_has_occurred);
                        return;
                }
            } else if (this instanceof EditInfoChangePassActivity) {
                switch (statusCode) {
                    case 500:
                        AlertMessage.showError(this, "Mật khẩu hiện tại không đúng \n Vui lòng kiểm tra lại");
                        break;
                    default:
                        AlertMessage.showError(this, R.string.an_error_has_occurred);
                        return;
                }
            } else if (this instanceof RegisterActivity) {
                String error = "";
                switch (statusCode) {
                    /*case 400:
                        error=getString(R.string.dialog_content_register_phone_invalid);
                        break;*/
                    case 500:
                        error = getString(R.string.dialog_content_register_phone_invalid);
                        break;
                    default:
                        AlertMessage.showError(this, R.string.an_error_has_occurred);
                        return;
                }
                LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), error, getString(R.string.ok), new LDialogUtils.Callback1() {
                    @Override
                    public void onClickButton0() {
                    }
                });
            } else if (!(this instanceof FacebookSignInActivity) && (statusCode == 403 || statusCode == 401)) {
                forceSignIn();
            } else if (this instanceof ForgotPasswordActivity && statusCode == 400) {
                AlertMessage.showError(this, R.string.email_not_found);
            } else if (this instanceof SignUpActivity && statusCode == 400) {
                AlertMessage.showError(this, R.string.email_duplicate);
            } else {
                try {
                    ResponseBody body = ((HttpException) throwable).response().errorBody();
                    Converter<ResponseBody, ErrorResponse> errorConverter =
                            RestClient.getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error = errorConverter.convert(body);
                    AlertMessage.showError(this, error.getError());
                } catch (Exception e) {
                    AlertMessage.showError(this, R.string.an_error_has_occurred);
                }
            }
        } else {
            AlertMessage.showError(this, R.string.an_error_has_occurred);
        }
        throwable.printStackTrace();
    }

    protected abstract boolean setFullScreen();

    protected abstract String setTag();

    protected abstract Activity setActivity();

    protected abstract int setLayoutResourceId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LUIUtil.transActivityFadeIn(activity);
    }
}
