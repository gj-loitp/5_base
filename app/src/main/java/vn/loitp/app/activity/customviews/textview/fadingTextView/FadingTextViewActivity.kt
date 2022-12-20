package vn.loitp.app.activity.customviews.textview.fadingTextView

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_fading_text_view.*
import vn.loitp.app.R


@LogTag("FadingTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FadingTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fading_text_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/rosenpin/fading-text-view"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FadingTextViewActivity::class.java.simpleName
        }

        val texts = arrayOf(
            "You",
            "can",
            "use",
            "an",
            "array",
            "resource",
            "or a string array",
            "as",
            "the parameter",
        )
        ftv.setTexts(texts)
        ftv.setTimeout(1L, java.util.concurrent.TimeUnit.SECONDS)

    }
}
