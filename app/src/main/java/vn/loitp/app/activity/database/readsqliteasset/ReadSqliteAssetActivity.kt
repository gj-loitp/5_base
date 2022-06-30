package vn.loitp.app.activity.database.readsqliteasset

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_read_sqlite_asset.*
import vn.loitp.app.R

@LogTag("ReadSqliteAssetActivity")
@IsFullScreen(false)
class ReadSqliteAssetActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_read_sqlite_asset
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val vocabularyManager = VocabularyManager(this)
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

        try {
            LUIUtil.printBeautyJson(o = vocabularyList[0], textView = textView)
        } catch (e: Exception) {
            textView.text = "$e"
        }
    }
}
