package vn.loitp.app.activity.database.readsqliteasset

import android.os.Bundle
import android.widget.TextView
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_read_sqlite_asset)
class ReadSqliteAssetActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vocabularyManager = VocabularyManager(activity)
        try {
            vocabularyManager.createDatabase()
            logD("init dtb success")
            showLongDebug("init dtb success")
        } catch (e: Exception) {
            logE("init dtb failed: $e")
            showDialogError("init dtb failed: $e")
            return
        }

        val vocabularyList = ArrayList(vocabularyManager.allVocabulary)
        logD("size: " + vocabularyList.size)

        val tv = findViewById<TextView>(R.id.textView)
        LUIUtil.printBeautyJson(vocabularyList[0], tv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
