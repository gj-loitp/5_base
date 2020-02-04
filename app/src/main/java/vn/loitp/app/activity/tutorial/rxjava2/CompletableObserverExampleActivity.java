package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vn.loitp.app.R;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class CompletableObserverExampleActivity extends BaseFontActivity {
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(view -> doSomeWork());
    }

    /*
     * simple example using CompletableObserver
     */
    private void doSomeWork() {
        Completable completable = Completable.timer(2000, TimeUnit.MILLISECONDS);
        completable
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCompletableObserver());
    }

    private CompletableObserver getCompletableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                LLog.d(getTAG(), " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete\n");
                LLog.d(getTAG(), " onComplete");
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage() + "\n");
                LLog.d(getTAG(), " onError : " + e.getMessage());
            }
        };
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_rxjava2_flowable;
    }
}
