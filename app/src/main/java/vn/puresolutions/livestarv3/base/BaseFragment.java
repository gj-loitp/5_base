package vn.puresolutions.livestarv3.base;

import android.content.Context;
import android.support.v4.app.Fragment;

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
import vn.puresolutions.livestar.activity.FacebookSignInActivity;
import vn.puresolutions.livestar.core.api.exception.NoConnectionException;
import vn.puresolutions.livestar.core.api.model.ErrorResponse;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.old.NetworkUtils;

/**
 * Created by khanh on 7/31/16.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected final String BASE_TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AlertMessage.closeAll();
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        //TODO maybe in some cases we don't need to check internet connection
        if (!NetworkUtils.hasConnection(context)) {
            subscriber.onError(new NoConnectionException());
            return;
        }

        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        compositeSubscription.add(subscription);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    protected void handleException(Throwable throwable) {
        if (throwable instanceof NoConnectionException) {
            AlertMessage.showError(context, R.string.cant_connect_to_internet);
        } else if (throwable instanceof SocketTimeoutException) {
            AlertMessage.showError(context, R.string.connect_timeout);
        } else if (throwable instanceof HttpException) {
            int statusCode = ((HttpException) throwable).code();
            if (statusCode == 204) {
                // isn't error
            } else if (!(context instanceof FacebookSignInActivity) && (statusCode == 403 || statusCode == 401)) {
                ((BaseActivity) getActivity()).forceSignIn();
            } else {
                try {
                    ResponseBody body = ((HttpException) throwable).response().errorBody();
                    Converter<ResponseBody, ErrorResponse> errorConverter =
                            RestClient.getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error = errorConverter.convert(body);
                    AlertMessage.showError(context, error.getError());
                } catch (Exception e) {
                    AlertMessage.showError(context, R.string.an_error_has_occurred);
                }
            }
        } else {
            AlertMessage.showError(context, R.string.an_error_has_occurred);
        }
        throwable.printStackTrace();
    }
}
