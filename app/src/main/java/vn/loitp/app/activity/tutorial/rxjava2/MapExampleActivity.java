package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vn.loitp.app.R;
import vn.loitp.app.activity.tutorial.rxjava2.model.ApiUser;
import vn.loitp.app.activity.tutorial.rxjava2.model.User;
import vn.loitp.app.activity.tutorial.rxjava2.util.RxJavaUtils;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class MapExampleActivity extends BaseFontActivity {
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
     * Here we are getting ApiUser Object from api server
     * then we are converting it into User Object because
     * may be our database support User Not ApiUser Object
     * Here we are using Map Operator to do that
     */
    private void doSomeWork() {
        getObservable()
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<ApiUser>, List<User>>() {

                    @Override
                    public List<User> apply(List<ApiUser> apiUsers) {
                        return RxJavaUtils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .subscribe(getObserver());
    }

    private Observable<List<ApiUser>> getObservable() {
        return Observable.create(listObservableEmitter -> {
            if (!listObservableEmitter.isDisposed()) {
                listObservableEmitter.onNext(RxJavaUtils.getApiUserList());
                listObservableEmitter.onComplete();
            }
        });
    }

    private Observer<List<User>> getObserver() {
        return new Observer<List<User>>() {

            @Override
            public void onSubscribe(Disposable d) {
                LLog.d(getTAG(), " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<User> userList) {
                textView.append(" onNext\n");
                for (User user : userList) {
                    textView.append(" firstname : " + user.firstname + "\n");
                }
                LLog.d(getTAG(), " onNext : " + userList.size());
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
