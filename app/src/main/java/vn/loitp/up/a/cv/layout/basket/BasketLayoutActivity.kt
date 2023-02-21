package vn.loitp.up.a.cv.layout.basket

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.yonder.basketlayout.BasketLayoutViewListener
import vn.loitp.databinding.ABasketLayoutBinding

@LogTag("BasketLayoutActivity")
@IsFullScreen(false)
class BasketLayoutActivity : BaseActivityFont() {
    private lateinit var binding: ABasketLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABasketLayoutBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = BasketLayoutActivity::class.java.simpleName
        }
        binding.basketView.apply {
            setBasketQuantity(quantity = 2)
            setMaxQuantity(maxQuantity = 5)
            setBasketLayoutListener(object : BasketLayoutViewListener {
                override fun onExceedMaxQuantity(quantity: Int) {
                    showShortInformation("Exceed max quantity: $quantity")
                }

                override fun onClickDecreaseQuantity(quantity: Int) {
                    showShortInformation("Decrease quantity to $quantity")
                    setDelay(1000) {
                        setBasketQuantity(quantity)
                    }
                }

                override fun onClickIncreaseQuantity(quantity: Int) {
                    showShortInformation("Increase quantity to $quantity")
                    setDelay(1000) {
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
