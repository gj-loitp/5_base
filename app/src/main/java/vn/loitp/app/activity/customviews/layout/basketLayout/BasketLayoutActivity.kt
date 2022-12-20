package vn.loitp.app.activity.customviews.layout.basketLayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.yonder.basketlayout.BasketLayoutViewListener
import kotlinx.android.synthetic.main.activity_basket_layout.*
import vn.loitp.app.R

@LogTag("BasketLayoutActivity")
@IsFullScreen(false)
class BasketLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_basket_layout
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
            this.tvTitle?.text = BasketLayoutActivity::class.java.simpleName
        }
        basketView.apply {
            setBasketQuantity(quantity = 2)
            setMaxQuantity(maxQuantity = 5)
            setBasketLayoutListener(object : BasketLayoutViewListener {
                override fun onExceedMaxQuantity(quantity: Int) {
                    showShortInformation("Exceed max quantity: $quantity")
                }

                override fun onClickDecreaseQuantity(quantity: Int) {
                    showShortInformation("Decrease quantity to $quantity")
                    LUIUtil.setDelay(1000) {
                        setBasketQuantity(quantity)
                    }
                }

                override fun onClickIncreaseQuantity(quantity: Int) {
                    showShortInformation("Increase quantity to $quantity")
                    LUIUtil.setDelay(1000) {
                        setBasketQuantity(quantity)
                    }
                }

                override fun onClickTrash() {
                    showShortInformation("on Click Trash Button")
                }
            })
        }
    }
}
