package vn.loitp.a.anim.morphTransitions

import android.os.Bundle
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_morph_transitions_full_screen.*
import vn.loitp.R

@LogTag("MorphTransitionsFullScreenActivity")
@IsFullScreen(false)
class MorphTransitionsFullScreenActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_morph_transitions_full_screen
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
            this.tvTitle?.text = MorphTransitionsFullScreenActivity::class.java.simpleName
        }
    }
}
