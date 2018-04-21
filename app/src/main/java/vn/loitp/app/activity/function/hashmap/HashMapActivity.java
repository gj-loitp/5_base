package vn.loitp.app.activity.function.hashmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.utils.util.ToastUtils;

public class HashMapActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv;
    private Map<String, String> map = new HashMap<String, String>();

    private int autoKey = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.bt_get_key_0).setOnClickListener(this);
        findViewById(R.id.bt_remove_key_0).setOnClickListener(this);
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
        return R.layout.activity_hashmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                map.put(String.valueOf(autoKey), "Value " + autoKey);
                printMap();
                autoKey++;
                break;
            case R.id.bt_get_key_0:
                String value = map.get(String.valueOf(0));
                ToastUtils.showShort("Click value= " + value);
                break;
            case R.id.bt_remove_key_0:
                map.remove(String.valueOf(0));
                printMap();
                break;
        }
    }

    private void printMap() {
        String s = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            s += entry.getKey() + " -> " + entry.getValue() + "\n";
        }
        tv.setText(s.isEmpty() ? "No data" : s);
    }
}
