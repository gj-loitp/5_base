package vn.loitp.app.activity.demo.ebookwithrealm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LSoundUtil;
//https://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/
public class EbookWithRealmActivity extends BaseActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_play).setOnClickListener(this);
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
        return R.layout.activity_ebook_with_realm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                LSoundUtil.startMusicFromAsset(activity, "ting.ogg");
                break;
        }
    }
}
