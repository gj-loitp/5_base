package vn.loitp.rxandroid;

import rx.Subscriber;

/**
 * Created by Phu Tran on 8/8/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public abstract class ApiSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        // do nothing
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinally(true);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
        onFinally(false);
    }

    public void onFinally(boolean success) {
        // DO NOTHING
    }

    public abstract void onSuccess(T result);
    public abstract void onFail(Throwable e);
}