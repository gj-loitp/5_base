package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vn.loitp.app.R;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class IntervalExampleActivity extends BaseFontActivity {
    Button btn;
    TextView textView;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);
        btn.setOnClickListener(view -> doSomeWork());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // clearing it : do not emit after destroy
    }

    /*
     * simple example using interval to run task at an interval of 1 sec
     * which start immediately
     */
    private void doSomeWork() {
        disposables.add(getObservable()
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }

    private Observable<? extends Long> getObservable() {
        return Observable.interval(0, 1, TimeUnit.SECONDS);
    }

    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long value) {
                textView.append(" onNext : value : " + value + "\n");
                LLog.d(getTAG(), " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage() + "\n");
                LLog.d(getTAG(), " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete\n");
                LLog.d(getTAG(), " onComplete");
            }
        };
    }
}
