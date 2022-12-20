package vn.loitp.app.activity.customviews.cardView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_card_view.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_sample.*
import vn.loitp.app.R


@LogTag("CardViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CardViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_card_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CardViewActivity::class.java.simpleName
        }

        btChange.setSafeOnClickListener {
            val radiusTL = resources.getDimension(R.dimen.round_small)
            val radiusTR = resources.getDimension(R.dimen.round_medium)
            val radiusBL = resources.getDimension(R.dimen.round_large)
            val radiusBR = resources.getDimension(R.dimen.round_largest)

            LUIUtil.setCornerCardView(
                cardView = mcvTest,
                radiusTL = radiusTL,
                radiusTR = radiusTR,
                radiusBL = radiusBL,
                radiusBR = radiusBR
            )
        }
    }
}
