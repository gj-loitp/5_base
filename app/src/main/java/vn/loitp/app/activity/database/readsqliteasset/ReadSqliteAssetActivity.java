package vn.loitp.app.activity.database.readsqliteasset;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

public class ReadSqliteAssetActivity extends BaseFontActivity {
    private VocabularyManager vocabularyManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyManager = new VocabularyManager(getActivity());
        try {
            vocabularyManager.createDatabase();
            LLog.d(getTAG(), "init dtb success");
        } catch (Exception e) {
            LLog.d(getTAG(), "init dtb failed: " + e.toString());
        }
        List<Vocabulary> vocabularyList = new ArrayList<>();
        vocabularyList.addAll(vocabularyManager.getAllVocabulary());

        LLog.d(getTAG(), "size: " + vocabularyList.size());

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
