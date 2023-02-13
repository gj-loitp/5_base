package vn.loitp.up.a.db.readSqliteAsset

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AReadSqliteAssetBinding

@LogTag("ReadSqliteAssetActivity")
@IsFullScreen(false)
class ReadSqliteAssetActivity : BaseActivityFont() {

    private lateinit var binding: AReadSqliteAssetBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AReadSqliteAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
            binding.textView.printBeautyJson(o = vocabularyList[0])
        } catch (e: Exception) {
            binding.textView.text = "$e"
        }
    }
}
