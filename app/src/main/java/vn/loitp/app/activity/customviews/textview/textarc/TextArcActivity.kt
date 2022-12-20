package vn.loitp.app.activity.customviews.textview.textarc

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.a_lab.textarc.TextArc
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_textarc.*


@LogTag("TextArcActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TextArcActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return vn.loitp.app.R.layout.activity_textarc
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
                            url = "https://github.com/AndroidLab/textarc"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(vn.loitp.app.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TextArcActivity::class.java.simpleName
        }

        val textArc = TextArc(this)
        textArc.setText("Text arc that was added programmatically")
        textArc.setRadius(255)
        textArc.setCenterAngle(-90)
        textArc.setTextColor(Color.GREEN)
        textArc.setTextSize(72)

        ll.addView(textArc)
    }
}
