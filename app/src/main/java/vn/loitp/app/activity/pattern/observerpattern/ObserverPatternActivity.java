package vn.loitp.app.activity.pattern.observerpattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;

//https://viblo.asia/p/android-design-patterns-the-observer-pattern-WAyK8xqpKxX
public class ObserverPatternActivity extends BaseActivity implements View.OnClickListener, RepositoryObserver {
    private Subject mUserDataRepository;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv = (TextView) findViewById(R.id.tv);

        mUserDataRepository = UserDataRepository.getInstance();
        mUserDataRepository.registerObserver(this);

        findViewById(R.id.bt).setOnClickListener(this);
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
        return R.layout.activity_observer_pattern;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                tv.setText("Loading...");
                UserDataRepository.getInstance().getNewDataFromRemote();
                break;
        }
    }

    @Override
    public void onUserDataChanged(String fullname, int age) {
        LToast.show(activity, "onUserDataChanged");
        tv.setText(fullname + " - " + age);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }
}
