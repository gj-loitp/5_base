package vn.loitp.a.cv.cv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setCornerCardView
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_card_view.*
import vn.loitp.R

@LogTag("CardViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CardViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_card_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CardViewActivityFont::class.java.simpleName
        }

        btChange.setSafeOnClickListener {
            val radiusTL = resources.getDimension(R.dimen.round_small)
            val radiusTR = resources.getDimension(R.dimen.round_medium)
            val radiusBL = resources.getDimension(R.dimen.round_large)
            val radiusBR = resources.getDimension(R.dimen.round_largest)

            mcvTest.setCornerCardView(
                radiusTL = radiusTL,
                radiusTR = radiusTR,
                radiusBL = radiusBL,
                radiusBR = radiusBR
            )
        }
    }
}
