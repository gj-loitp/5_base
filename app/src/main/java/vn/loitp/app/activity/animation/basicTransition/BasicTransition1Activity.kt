package vn.loitp.app.activity.animation.basicTransition

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_basic_transition_1.*
import vn.loitp.app.R

@LogTag("BasicTransition1Activity")
@IsFullScreen(false)
class BasicTransition1Activity : BaseFontActivity() {

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_basic_transition_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BasicTransition1Activity::class.java.simpleName
        }
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_2,
            imageView = imageViewItem,
        )
        ViewCompat.setTransitionName(imageViewItem, IV)
        ViewCompat.setTransitionName(textView, TV)
    }
}
