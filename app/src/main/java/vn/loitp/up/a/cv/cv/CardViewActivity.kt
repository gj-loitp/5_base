package vn.loitp.up.a.cv.cv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setCornerCardView
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ACardViewBinding

@LogTag("CardViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CardViewActivity : BaseActivityFont() {
    private lateinit var binding: ACardViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACardViewBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = CardViewActivity::class.java.simpleName
        }

        binding.btChange.setSafeOnClickListener {
            val radiusTL = resources.getDimension(R.dimen.round_small)
            val radiusTR = resources.getDimension(R.dimen.round_medium)
            val radiusBL = resources.getDimension(R.dimen.round_large)
            val radiusBR = resources.getDimension(R.dimen.round_largest)

            binding.mcvTest.setCornerCardView(
                radiusTL = radiusTL,
                radiusTR = radiusTR,
                radiusBL = radiusBL,
                radiusBR = radiusBR
            )
        }
    }
}
