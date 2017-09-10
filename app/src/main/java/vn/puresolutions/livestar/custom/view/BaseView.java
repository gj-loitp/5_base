package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

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
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestar.activity.FacebookSignInActivity;
import vn.puresolutions.livestar.core.api.exception.NoConnectionException;
import vn.puresolutions.livestar.core.api.model.ErrorResponse;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.old.NetworkUtils;

/**
 * Created by Phu Tran on 8/16/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class BaseView extends FrameLayout {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void addView(int layoutId) {
        LayoutInflater.from(getContext()).inflate(layoutId, this);
    }

    public void onDestroy() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        //TODO maybe in some cases we don't need to check internet connection
        if (!NetworkUtils.hasConnection(getContext())) {
            subscriber.onError(new NoConnectionException());
            return;
        }

        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        compositeSubscription.add(subscription);
    }

    protected void handleException(Throwable throwable) {
        if (throwable instanceof NoConnectionException) {
            AlertMessage.showError(getContext(), R.string.cant_connect_to_internet);
        } else if (throwable instanceof SocketTimeoutException) {
            AlertMessage.showError(getContext(), R.string.connect_timeout);
        } else if (throwable instanceof HttpException) {
            int statusCode = ((HttpException) throwable).code();
            if (statusCode == 204) {
                // isn't error
            } else if (!(getContext() instanceof FacebookSignInActivity) && (statusCode == 403 || statusCode == 401)) {
                AlertMessage.showError(getContext(), R.string.login_again);
                ((BaseActivity)getContext()).forceSignIn();
            } else {
                try {
                    ResponseBody body = ((HttpException) throwable).response().errorBody();
                    Converter<ResponseBody, ErrorResponse> errorConverter =
                            RestClient.getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error = errorConverter.convert(body);
                    AlertMessage.showError(getContext(), error.getError());
                } catch (Exception e) {
                    AlertMessage.showError(getContext(), R.string.an_error_has_occurred);
                }
            }
        } else {
            AlertMessage.showError(getContext(), R.string.an_error_has_occurred);
        }
        throwable.printStackTrace();
    }
}
