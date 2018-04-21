package vn.loitp.app.activity.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.galleryAPI.GalleryAPIActivity;
import vn.loitp.app.activity.api.test.TestAPIActivity;
import vn.loitp.app.activity.api.truyentranhtuan.TTTAPIMenuActivity;
import vn.loitp.app.activity.database.readsqliteasset.ReadSqliteAssetActivity;
import vn.loitp.app.activity.database.realm.RealmActivity;
import vn.loitp.app.activity.database.sqlite.SqliteActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.restapi.restclient.RestClient;

public class MenuDatabaseActivity extends BaseActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_sqlite).setOnClickListener(this);
        findViewById(R.id.bt_realm).setOnClickListener(this);
        findViewById(R.id.bt_sqlite_asset).setOnClickListener(this);
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
        return R.layout.activity_menu_database;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_sqlite:
                intent = new Intent(activity, SqliteActivity.class);
                break;
            case R.id.bt_realm:
                intent = new Intent(activity, RealmActivity.class);
                break;
            case R.id.bt_sqlite_asset:
                intent = new Intent(activity, ReadSqliteAssetActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
