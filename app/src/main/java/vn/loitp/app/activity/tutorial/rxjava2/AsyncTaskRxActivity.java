package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import loitp.basemaster.R;
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike;

public class AsyncTaskRxActivity extends BaseFontActivity implements View.OnClickListener {
    private TextView tv;
    private Test1 test1;
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_async_task).setOnClickListener(this);
        findViewById(R.id.bt_rx_1).setOnClickListener(this);
        findViewById(R.id.bt_rx_2).setOnClickListener(this);
        findViewById(R.id.bt_rx_3).setOnClickListener(this);
        findViewById(R.id.bt_rx_4).setOnClickListener(this);
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
        return R.layout.activity_asynctask_rx;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_async_task:
                if (test1 != null) {
                    test1.cancel(true);
                }
                test1 = new Test1();
                test1.execute();
                break;
            case R.id.bt_rx_1:
                if (disposable != null) {
                    disposable.dispose();
                }
                final MyRxTask1 myRxTask1 = new MyRxTask1(tv);
                disposable = myRxTask1.execute();
                compositeDisposable.add(disposable);
                break;
            case R.id.bt_rx_2:
                if (disposable != null) {
                    disposable.dispose();
                    return;
                }
                final MyRxTask2 myRxTask2 = new MyRxTask2(tv);
                disposable = myRxTask2.execute();
                compositeDisposable.add(disposable);
                break;
            case R.id.bt_rx_3:
                final Test2 test2 = new Test2(6);
                test2.apply();
                break;
            case R.id.bt_rx_4:
                testWithProgress();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (test1 != null) {
            test1.cancel(true);
        }
        compositeDisposable.clear();
    }

    private class Test1 extends AsyncTask<Void, Bike, List<Bike>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("onPreExecute\n");
        }

        @Override
        protected List<Bike> doInBackground(Void... voids) {
            final List<Bike> bikeList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    final Bike bike = new Bike();
                    bike.setModel("Model " + i);
                    bike.setName("Name " + i);
                    bikeList.add(bike);
                    publishProgress(bike);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return bikeList;
        }

        @Override
        protected void onProgressUpdate(Bike... values) {
            super.onProgressUpdate(values);
            final Bike bike = values[0];
            tv.append(bike.getName() + " - " + bike.getModel() + "\n");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tv.append("onCancelled\n");
        }

        @Override
        protected void onPostExecute(List<Bike> bikeList) {
            super.onPostExecute(bikeList);
            tv.append("onPostExecute\n");
        }
    }

    private class Test2 {
        private int count;

        Test2(int count) {
            this.count = count;
        }

        public void apply() {
            tv.setText("Prev\n");

            /*PublishSubject publishSubject = new PublishSubject<>();
            publishSubject.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {

            }*/
            Single.fromCallable(() -> {
                List<Bike> bikeList = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Thread.sleep(1000);
                    Bike bike = new Bike();
                    bike.setName("Name " + i);
                    bike.setModel("Model " + i);
                    bikeList.add(bike);
                }
                return bikeList;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Bike>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            tv.append("onSubscribe\n");
                        }

                        @Override
                        public void onSuccess(List<Bike> bikes) {
                            tv.append("onSuccess\n");
                        }

                        @Override
                        public void onError(Throwable e) {
                            tv.append("onError\n");
                        }
                    });

        }
    }

    private void testWithProgress() {
        tv.setText("prev testWithProgress\n");
        compositeDisposable.clear();
        final int count = 10;
        final TestAsyncKotlin testAsyncKotlin = new TestAsyncKotlin(count);
        final Disposable disProgress = testAsyncKotlin.subscribeProgression(integer -> {
            LLog.d("loitptest", "Home -> subscribeProgression " + integer);
            tv.append("Home -> subscribeProgression " + integer + "\n");
            return Unit.INSTANCE;
        });
        final Disposable dis = testAsyncKotlin.apply(aBoolean -> {
                    LLog.d("loitptest", "Home -> 1 aBoolean: " + aBoolean);
                    tv.append("Home -> 1 aBoolean: " + aBoolean + "\n");
                    return Unit.INSTANCE;
                }, throwable -> {
                    LLog.d("loitptest", "Home -> 2 throwable: " + throwable.toString());
                    tv.append("Home -> 2 throwable: " + throwable.toString() + "\n");
                    return Unit.INSTANCE;
                }, () -> {
                    LLog.d("loitptest", "Home -> on disposed");
                    tv.append("Home -> on disposed\n");
                    return Unit.INSTANCE;
                },
                () -> {
                    LLog.d("loitptest", "Home -> 3 finished");
                    tv.append("Home -> 3 finished\n");
                    return Unit.INSTANCE;
                });
        compositeDisposable.add(disProgress);
        compositeDisposable.add(dis);
    }
}
