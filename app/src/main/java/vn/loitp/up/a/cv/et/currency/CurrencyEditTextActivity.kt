package vn.loitp.up.a.cv.et.currency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AEtCurrencyBinding

@LogTag("CurrencyEditTextActivity")
@IsFullScreen(false)
class CurrencyEditTextActivity : BaseActivityFont() {
    private lateinit var binding: AEtCurrencyBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CurrencyEditTextActivity::class.java.simpleName
        }
        binding.currencyEditText.onTextChanged = { numDot, numNoDot ->
            binding.tvCurrency.text = "$numDot -> $numNoDot"
        }
    }
}
