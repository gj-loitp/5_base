package vn.loitp.up.a.func.keyboardVisibility

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.loitp.itf.OnKeyboardVisibilityListener
import vn.loitp.R
import vn.loitp.databinding.AKbVisibilityBinding
import vn.loitp.up.a.func.keyboard.KeyboardActivity


@LogTag("KeyboardVisibilityActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class KeyboardVisibilityActivity : BaseActivityFont() {

    private lateinit var binding: AKbVisibilityBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AKbVisibilityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setKeyboardVisibilityListener(object : OnKeyboardVisibilityListener {
            override fun onVisibilityChanged(visible: Boolean) {
                showShortInformation("OnKeyboardVisibilityListener visible $visible")
            }
        })
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = KeyboardActivity::class.java.simpleName
        }

        binding.btShow.setSafeOnClickListener {
            this.showKeyboard()
        }
        binding.btHide.setSafeOnClickListener {
            this.hideKeyboard()
        }
    }
}
