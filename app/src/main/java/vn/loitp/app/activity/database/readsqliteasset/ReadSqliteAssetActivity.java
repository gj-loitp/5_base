package vn.loitp.app.activity.database.readsqliteasset;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

public class ReadSqliteAssetActivity extends BaseActivity {
    private VocabularyManager vocabularyManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyManager = new VocabularyManager(activity);
        try {
            vocabularyManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (IOException e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
        }
        List<Vocabulary> vocabularyList = new ArrayList<>();
        vocabularyList.addAll(vocabularyManager.getAllVocabulary());

        LLog.d(TAG, "size: " + vocabularyList.size());

        tv = (TextView) findViewById(R.id.tv);
        LUIUtil.printBeautyJson(vocabularyList.get(0), tv);
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
        return R.layout.activity_read_sqlite_asset;
    }

}
