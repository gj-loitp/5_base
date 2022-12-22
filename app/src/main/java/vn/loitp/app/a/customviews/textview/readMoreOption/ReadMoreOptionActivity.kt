package vn.loitp.app.a.customviews.textview.readMoreOption

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.devs.readmoreoption.ReadMoreOption
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_read_more_option.*
import vn.loitp.R

@LogTag("ReadMoreOptionActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ReadMoreOptionActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_read_more_option
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
                it.setImageResource(R.drawable.ic_baseline_code_48)
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

        readMoreOption.addReadMoreTo(tv, getString(R.string.i_love_you))
    }
}
