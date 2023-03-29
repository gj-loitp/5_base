package vn.loitp.up.a.cv.bt.l

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALButtonBinding

@LogTag("LButtonActivity")
@IsFullScreen(false)
class LButtonActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: ALButtonBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALButtonBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = LButtonActivity::class.java.simpleName
        }

        binding.bt0.setOnClickListener(this)

        binding.bt1.setPressedDrawable(R.drawable.l_circle_color_primary)
        binding.bt1.setOnClickListener(this)

        binding.bt2.setPressedDrawable(R.drawable.l_bt_color_primary)
        binding.bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.bt0, binding.bt1, binding.bt2 ->
                showShortInformation(msg = getString(R.string.click))
        }
    }
}
