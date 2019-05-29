package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import loitp.basemaster.R;
import vn.loitp.app.activity.tutorial.rxjava2.model.Bike;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

public class AsyncTaskRxActivity extends BaseFontActivity implements View.OnClickListener {
    private TextView tv;
    private Test1 test1;
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG,"1");
        //LLog.d(TAG,"2");
        //LLog.d(TAG,"3");
        //LLog.d(TAG,"4");
        //LLog.d(TAG,"5");
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_async_task).setOnClickListener(this);
        findViewById(R.id.bt_rx_1).setOnClickListener(this);
        findViewById(R.id.bt_rx_2).setOnClickListener(this);
        findViewById(R.id.bt_rx_3).setOnClickListener(this);
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
                MyRxTask1 myRxTask1 = new MyRxTask1(tv);
                disposable = myRxTask1.execute();
                compositeDisposable.add(disposable);
                break;
            case R.id.bt_rx_2:
                if (disposable != null) {
                    disposable.dispose();
                }
                MyRxTask2 myRxTask2 = new MyRxTask2(tv);
                disposable = myRxTask2.execute();
                compositeDisposable.add(disposable);
                break;
            case R.id.bt_rx_3:
                Test2 test2 = new Test2(6);
                test2.apply();
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
            List<Bike> bikeList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    Bike bike = new Bike();
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
            Bike bike = values[0];
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
}
