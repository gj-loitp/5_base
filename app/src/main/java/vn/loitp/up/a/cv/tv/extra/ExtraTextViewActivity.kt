package vn.loitp.up.a.cv.tv.extra

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.views.tv.extra.LToggleLExtraTextView
import vn.loitp.databinding.ATvExtraBinding

//https://github.com/chuross/extra-textview
@LogTag("ExtraTextViewActivity")
@IsFullScreen(false)
class ExtraTextViewActivity : BaseActivityFont() {
    private lateinit var binding: ATvExtraBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvExtraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.toggleTxt.setSafeOnClickListener {
            binding.toggleTxt.toggle()
        }

        binding.btWaiting.setSafeOnClickListener {
            if (binding.toggleTxt.getState() == LToggleLExtraTextView.State.WAITING) {
                binding.toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                binding.toggleTxt.setState(LToggleLExtraTextView.State.WAITING)
            }
        }

        binding.btActive.setSafeOnClickListener {
            if (binding.toggleTxt.getState() == LToggleLExtraTextView.State.ACTIVE) {
                binding.toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                binding.toggleTxt.setState(LToggleLExtraTextView.State.ACTIVE)
            }
        }
    }

}
