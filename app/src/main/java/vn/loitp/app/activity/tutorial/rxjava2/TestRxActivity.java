package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.statusbar.LThreadUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vn.loitp.app.R;
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike;
import vn.loitp.app.app.LApplication;

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
        tv = findViewById(R.id.tv);
        findViewById(R.id.bt0).setOnClickListener(this);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        findViewById(R.id.bt_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt0:
                test0();
                break;
            case R.id.bt1:
                test1();
                break;
            case R.id.bt2:
                test2();
                break;
            case R.id.bt3:
                test3();
                break;
            case R.id.bt_4:
                test4();
                break;
            case R.id.bt_5:
                test5();
                break;
        }
    }

    private void print(String s) {
        LLog.d(getTAG(), s + " -> isUIThread: " + LThreadUtil.INSTANCE.isUIThread() + "\n");
        tv.append(s + " -> isUIThread: " + LThreadUtil.INSTANCE.isUIThread() + "\n");
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

    private Bike bike;

    private void test2() {
        tv.setText("test2\n");
        bike = new Bike("Suzuki", "GSX R1000");

        //c1
        //Observable<Bike> bikeObservable = Observable.just(bike);

        //c2
        Observable<Bike> bikeObservable = Observable.defer(() -> Observable.just(bike));
        bike = new Bike("Honda", "CBR1000RR");
        bikeObservable.subscribe(new Observer<Bike>() {
            @Override
            public void onSubscribe(Disposable d) {
                print("onSubscribe");
            }

            @Override
            public void onNext(Bike bike) {
                print("onNext " + bike.getName() + " - " + bike.getModel());
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

    private Disposable interValDisposable;

    private void test3() {
        tv.setText("test3\n");
        interValDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<Long>() {
                            @Override
                            public void onNext(Long aLong) {
                                print("onNext " + aLong);
                                if (aLong == 5) {
                                    if (interValDisposable != null) {
                                        interValDisposable.dispose();
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                print("onComplete");
                            }
                        }
                );
    }

    private void test4() {
        tv.setText("test4\n");
        Disposable disposable = Observable.create((ObservableOnSubscribe<Bike>) emitter -> {
            List<Bike> bikeList = getBikeList();
            for (Bike bike : bikeList) {
                emitter.onNext(bike);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Bike>() {
                    /*@Override
                    public void onSubscribe(Disposable d) {
                        print("onSubscribe");
                    }*/

                    @Override
                    public void onNext(Bike bike) {
                        print("onNext " + bike.getName() + " - " + bike.getModel());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        print("onComplete");
                    }
                });
        //compositeDisposable.add(disposable);
    }

    //for test 5
    private Observable<String> searchBike(String b) {
        return Observable.just(LApplication.Companion.getGson().toJson(getBikeList()));
    }

    private List<Bike> parse(String json) {
        return LApplication.Companion.getGson().fromJson(json, new TypeToken<List<Bike>>() {
        }.getType());
    }

    private void test5() {
        tv.setText("test5\n");

        //map
        //ok
        /*searchBike("Yamaha").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, List<Bike>>() {
                    @Override
                    public List<Bike> apply(String s) throws Exception {
                        return parse(s);
                    }
                })
                .subscribe(new Observer<List<Bike>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        print("onSubscribe");
                    }

                    @Override
                    public void onNext(List<Bike> bike) {
                        print("onNext " + bike.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        print("onComplete");
                    }
                });*/

        //flat map
        searchBike("Yamaha")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<String, ObservableSource<List<Bike>>>) s -> Observable.defer(() -> Observable.just(parse(s))))
                .flatMap((Function<List<Bike>, ObservableSource<Bike>>) Observable::fromIterable)
                .subscribe(new Observer<Bike>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        print("onSubscribe");
                    }

                    @Override
                    public void onNext(Bike b) {
                        print("onNext " + b.getName());
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
    //for test 5
}
