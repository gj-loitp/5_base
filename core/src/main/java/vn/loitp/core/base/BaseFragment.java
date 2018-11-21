package vn.loitp.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.core.R;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.exception.NoConnectionException;

/**
 * Created by khanh on 7/31/16.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected final String BASE_TAG = BaseFragment.class.getSimpleName();

    protected View frmRootView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentCallback != null) {
            fragmentCallback.onViewCreated();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frmRootView = inflater.inflate(setLayoutResourceId(), container, false);
        return frmRootView;
    }

    protected abstract int setLayoutResourceId();

    @Override
    public void onDestroyView() {
        LDialogUtil.clearAll();
        super.onDestroyView();
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        if (!LConnectivityUtil.isConnected(getActivity())) {
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
        if (throwable == null) {
            return;
        }
        showDialogError(throwable.getMessage());
    }

    protected void showDialogError(String errMsg) {
        LDialogUtil.showDialog1(getActivity(), getString(R.string.warning), errMsg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                //getActivity().onBackPressed();
            }
        });
    }

    protected void showDialogMsg(String msg) {
        LDialogUtil.showDialog1(getActivity(), getString(R.string.app_name), msg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                //getActivity().onBackPressed();
            }
        });
    }

    public interface FragmentCallback {
        public void onViewCreated();
    }

    protected FragmentCallback fragmentCallback;

    public void setFragmentCallback(FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }
}
