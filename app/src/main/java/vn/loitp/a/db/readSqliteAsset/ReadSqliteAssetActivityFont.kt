package vn.loitp.a.db.readSqliteAsset

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_read_sqlite_asset.*
import vn.loitp.R

@LogTag("ReadSqliteAssetActivity")
@IsFullScreen(false)
class ReadSqliteAssetActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_read_sqlite_asset
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ReadSqliteAssetActivityFont::class.java.simpleName
        }

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
            textView.printBeautyJson(o = vocabularyList[0])
        } catch (e: Exception) {
            textView.text = "$e"
        }
    }
}
