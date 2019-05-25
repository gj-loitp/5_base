package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import loitp.basemaster.R;
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.statusbar.LThreadUtil;

//https://viblo.asia/p/cung-hoc-rxjava-phan-1-gioi-thieu-aRBeXWqgGWE
public class TestRxActivity extends BaseFontActivity implements View.OnClickListener {
    private TextView tv;

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
        return R.layout.activity_test_rx;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_0).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_0:
                test0();
                break;
            case R.id.bt_1:
                test1();
                break;
        }
    }

    private void print(String s) {
        tv.append(s + " -> isUIThread: " + LThreadUtil.isUIThread() + "\n");
    }

    private List<Bike> getBikeList() {
        List<Bike> bikeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Bike bike = new Bike("Name " + i, "Model " + i);
            bikeList.add(bike);
        }
        return bikeList;
    }

    private void test0() {
        tv.setText("test0\n");
        Observable.fromArray("Suzuki", "Ducati", "BMW", "Honda").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                print("onSubscribe");
            }

            @Override
            public void onNext(String string) {
                print("onNext " + string);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                print("onComplete");
            }
        });
    }

    private void test1() {
        tv.setText("test1\n");
        Observable.just(getBikeList()).subscribe(new Observer<List<Bike>>() {
            @Override
            public void onSubscribe(Disposable d) {
                print("onSubscribe");
            }

            @Override
            public void onNext(List<Bike> bikes) {
                print("onNext " + bikes.size());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                print("onComplete");
            }
        });
    }
}
