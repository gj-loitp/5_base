package vn.loitp.app.activity.customviews.textview.readMoreOption

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.devs.readmoreoption.ReadMoreOption
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_read_more_option.*


@LogTag("ReadMoreOptionActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ReadMoreOptionActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return vn.loitp.app.R.layout.activity_read_more_option
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
                            url = "https://github.com/devendroid/ReadMoreOption"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(vn.loitp.app.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ReadMoreOptionActivity::class.java.simpleName
        }

        val readMoreOption = ReadMoreOption.Builder(this)
            .textLength(3, ReadMoreOption.TYPE_LINE) // OR
            //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
            .moreLabel("MORE")
            .lessLabel("LESS")
            .moreLabelColor(Color.RED)
            .lessLabelColor(Color.BLUE)
            .labelUnderLine(true)
            .expandAnimation(true)
            .build()

        readMoreOption.addReadMoreTo(tv, getString(vn.loitp.app.R.string.i_love_you))
    }
}
