package vn.loitp.up.a.cv.layout.flow

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getRandomString
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFlowLayoutBinding

@LogTag("FlowLayoutActivity")
@IsFullScreen(false)
class FlowLayoutActivity : BaseActivityFont() {
    private lateinit var binding: AFlowLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFlowLayoutBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FlowLayoutActivity::class.java.simpleName
        }
        for (i in 0..20) {
            val tv = TextView(this)
            tv.text = getRandomString(15)
            tv.setBackgroundResource(R.drawable.bt_tag)
            binding.flowLayout.addView(tv)
        }
    }
}
