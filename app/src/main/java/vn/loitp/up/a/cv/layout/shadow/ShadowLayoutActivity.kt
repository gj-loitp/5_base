package vn.loitp.up.a.cv.layout.shadow

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utils.ConvertUtils
import vn.loitp.R
import vn.loitp.databinding.ALayoutShadowBinding

@LogTag("ShadowLayoutActivity")
@IsFullScreen(false)
class ShadowLayoutActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: ALayoutShadowBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutShadowBinding.inflate(layoutInflater)
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/lijiankun24/ShadowLayout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShadowLayoutActivity::class.java.simpleName
        }
        binding.tvChangeOval.setOnClickListener(this)
        binding.tvChangeRadius.setOnClickListener(this)
        binding.tvChangeRectangle.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.tvChangeOval -> binding.slOval.setShadowColor(getColor(R.color.black50))
            binding.tvChangeRadius -> binding.slRectangle.setShadowColor(Color.parseColor("#EE00FF7F"))
            binding.tvChangeRectangle -> binding.slRadius.setShadowRadius(
                ConvertUtils.dp2px(12f).toFloat()
            )
        }
    }
}
