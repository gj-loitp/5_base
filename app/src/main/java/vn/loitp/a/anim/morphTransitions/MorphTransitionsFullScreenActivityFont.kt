package vn.loitp.a.anim.morphTransitions

import android.os.Bundle
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_morph_transitions_full_screen.*
import vn.loitp.R

@LogTag("MorphTransitionsFullScreenActivity")
@IsFullScreen(false)
class MorphTransitionsFullScreenActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_morph_transitions_full_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        FabTransform.setup(this, rootView)
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MorphTransitionsFullScreenActivityFont::class.java.simpleName
        }
    }
}
