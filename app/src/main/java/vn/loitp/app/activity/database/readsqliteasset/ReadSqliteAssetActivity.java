package vn.loitp.app.activity.database.readsqliteasset;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

public class ReadSqliteAssetActivity extends BaseFontActivity {
    private VocabularyManager vocabularyManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyManager = new VocabularyManager(activity);
        try {
            vocabularyManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (Exception e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
        }
        List<Vocabulary> vocabularyList = new ArrayList<>();
        vocabularyList.addAll(vocabularyManager.getAllVocabulary());

        LLog.d(TAG, "size: " + vocabularyList.size());

        tv = findViewById(R.id.tv);
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
