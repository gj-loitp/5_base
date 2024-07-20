package vn.loitp.up.a.cv.bt.q

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AQButtonBinding

@LogTag("QButtonActivity")
@IsFullScreen(false)
class QButtonActivity : BaseActivityFont() {
    private lateinit var binding: AQButtonBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AQButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = QButtonActivity::class.java.simpleName
        }
        binding.btn.setCornerRadious(5)
        binding.btn.setStrokeWidth(5)
        binding.btn.setStrokeDashGap(5)
        binding.btn.setStrokeDashWidth(90)
        binding.btn.setBackgroundColor(getColor(R.color.colorPrimary))
        binding.btn.setStrokeColor(getColor(R.color.colorPrimaryDark))
    }
}
