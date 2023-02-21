package vn.loitp.up.a.cv.layout.heart

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALayoutHeartBinding
import java.util.*

@LogTag("HeartLayoutActivity")
@IsFullScreen(false)
class HeartLayoutActivity : BaseActivityFont() {
    private val mRandom = Random()
    private lateinit var binding: ALayoutHeartBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_heart
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutHeartBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = HeartLayoutActivity::class.java.simpleName
        }
        binding.rootView.setOnClickListener {
            binding.heartLayout.addHeart(randomColor())
        }
    }

    private fun randomColor(): Int {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
    }
}
