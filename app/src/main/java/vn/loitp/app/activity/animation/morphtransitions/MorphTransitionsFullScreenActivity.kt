package vn.loitp.app.activity.animation.morphtransitions

import android.os.Bundle
import com.animation.morphtransitions.FabTransform
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_morph_transitions_full_screen.*
import vn.loitp.app.R

@LogTag("MorphTransitionsFullScreenActivity")
@IsFullScreen(false)
class MorphTransitionsFullScreenActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_morph_transitions_full_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FabTransform.setup(this, rootView)
    }
}
