package vn.loitp.up.a.anim.morphTransitions

import android.os.Bundle
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMorphTransitionsFullScreenBinding

@LogTag("MorphTransitionsFullScreenActivity")
@IsFullScreen(false)
class MorphTransitionsFullScreenActivity : BaseActivityFont() {

    private lateinit var binding: AMorphTransitionsFullScreenBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMorphTransitionsFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        FabTransform.setup(this, binding.rootView)
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MorphTransitionsFullScreenActivity::class.java.simpleName
        }
    }
}
