package vn.loitp.app.a.db.readSqliteAsset

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_read_sqlite_asset.*
import vn.loitp.R

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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ReadSqliteAssetActivity::class.java.simpleName
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
            LUIUtil.printBeautyJson(o = vocabularyList[0], textView = textView)
        } catch (e: Exception) {
            textView.text = "$e"
        }
    }
}
