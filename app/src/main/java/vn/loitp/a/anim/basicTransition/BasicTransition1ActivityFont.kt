package vn.loitp.a.anim.basicTransition

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_animation_basic_transition_1.*
import vn.loitp.R

@LogTag("BasicTransition1Activity")
@IsFullScreen(false)
class BasicTransition1ActivityFont : BaseActivityFont() {

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_basic_transition_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BasicTransition1ActivityFont::class.java.simpleName
        }
        imageViewItem.loadGlide(
            any = Constants.URL_IMG_2,
        )
        ViewCompat.setTransitionName(imageViewItem, IV)
        ViewCompat.setTransitionName(textView, TV)
    }
}
