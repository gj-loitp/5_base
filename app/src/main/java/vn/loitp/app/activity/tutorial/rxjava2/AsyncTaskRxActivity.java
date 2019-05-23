package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
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
                test1 = new Test1();
                test1.execute();
                break;
            case R.id.bt_rx:
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

    private class Test1 extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("onPreExecute\n");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            tv.append(progress + "\n");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tv.append("onCancelled\n");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.append("onPostExecute\n");
        }
    }
}
