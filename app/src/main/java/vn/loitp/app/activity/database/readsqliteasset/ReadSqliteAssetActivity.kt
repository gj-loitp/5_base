package vn.loitp.app.activity.database.readsqliteasset

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import loitp.basemaster.R
import java.util.*

class ReadSqliteAssetActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vocabularyManager = VocabularyManager(activity)
        try {
            vocabularyManager.createDatabase()
            LLog.d(TAG, "init dtb success")
            showLongDebug("init dtb success")
        } catch (e: Exception) {
            LLog.e(TAG, "init dtb failed: $e")
            showDialogError("init dtb failed: $e")
            return
        }

        val vocabularyList = ArrayList(vocabularyManager.allVocabulary)
        LLog.d(TAG, "size: " + vocabularyList.size)

        val tv = findViewById<TextView>(R.id.tv)
        LUIUtil.printBeautyJson(vocabularyList[0], tv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_read_sqlite_asset
    }

}
