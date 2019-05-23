package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import rx.Observer;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vn.loitp.core.base.BaseFontActivity;

public class AsyncTaskRxActivity extends BaseFontActivity implements View.OnClickListener {
    private TextView tv;
    private Test1 test1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_async_task).setOnClickListener(this);
        findViewById(R.id.bt_rx).setOnClickListener(this);
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
            case R.id.bt_rx:
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
            tv.append(bike.name + " - " + bike.model + "\n");
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
            }).map(bikes -> {
                return bikes;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Bike>>() {
                        @Override
                        public void onCompleted() {
                            tv.append("Result\n");
                        }

                        @Override
                        public void onError(Throwable e) {
                            tv.append("onError\n");
                        }

                        @Override
                        public void onNext(List<Bike> bikes) {
                            tv.append("onNext\n");
                        }
                    });

        }
    }

    private class Bike {
        private String name;
        private String model;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
