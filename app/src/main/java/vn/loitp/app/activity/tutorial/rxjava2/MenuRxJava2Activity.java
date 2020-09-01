package vn.loitp.app.activity.tutorial.rxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import vn.loitp.app.R;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class MenuRxJava2Activity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt0).setOnClickListener(this);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
        findViewById(R.id.bt_00).setOnClickListener(this);
        findViewById(R.id.bt_test_rx).setOnClickListener(this);
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
        return R.layout.activity_menu_rx_java2;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt0:
                intent = new Intent(getActivity(), DisposableExampleActivity.class);
                break;
            case R.id.bt1:
                intent = new Intent(getActivity(), FlowableExampleActivity.class);
                break;
            case R.id.bt2:
                intent = new Intent(getActivity(), IntervalExampleActivity.class);
                break;
            case R.id.bt3:
                intent = new Intent(getActivity(), SingleObserverExampleActivity.class);
                break;
            case R.id.bt4:
                intent = new Intent(getActivity(), CompletableObserverExampleActivity.class);
                break;
            case R.id.bt5:
                intent = new Intent(getActivity(), MapExampleActivity.class);
                break;
            case R.id.bt_00:
                intent = new Intent(getActivity(), AsyncTaskRxActivity.class);
                break;
            case R.id.bt_test_rx:
                intent = new Intent(getActivity(), TestRxActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(getActivity());
        }
    }
}
