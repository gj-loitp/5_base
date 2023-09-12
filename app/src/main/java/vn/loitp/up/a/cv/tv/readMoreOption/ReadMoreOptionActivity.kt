package vn.loitp.up.a.cv.tv.readMoreOption

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.devs.readmoreoption.ReadMoreOption
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATvReadMoreOptionBinding

@LogTag("ReadMoreOptionActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ReadMoreOptionActivity : BaseActivityFont() {
    private lateinit var binding: ATvReadMoreOptionBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvReadMoreOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/devendroid/ReadMoreOption"
                    )
                })
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ReadMoreOptionActivity::class.java.simpleName
        }

        val readMoreOption =
            ReadMoreOption.Builder(this).textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("MORE").lessLabel("LESS").moreLabelColor(Color.RED)
                .lessLabelColor(Color.BLUE).labelUnderLine(true).expandAnimation(true).build()

        readMoreOption.addReadMoreTo(binding.tv, getString(R.string.i_love_you))
    }
}
