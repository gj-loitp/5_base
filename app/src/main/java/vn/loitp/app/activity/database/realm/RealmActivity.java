package vn.loitp.app.activity.database.realm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class RealmActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_realm).setOnClickListener(this);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_realm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_realm:
                Intent intent = new Intent(activity, EbookWithRealmActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
                break;
        }
    }
}
