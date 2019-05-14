package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class CompletableObserverExampleActivity extends BaseFontActivity {
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
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
                LLog.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete\n");
                LLog.d(TAG, " onComplete");
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage() + "\n");
                LLog.d(TAG, " onError : " + e.getMessage());
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
