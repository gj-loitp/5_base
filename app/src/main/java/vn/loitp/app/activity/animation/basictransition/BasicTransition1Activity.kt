package vn.loitp.app.activity.animation.basictransition

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_basic_transition_1.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_animation_basic_transition_1)
@LogTag("BasicTransition1Activity")
@IsFullScreen(false)
class BasicTransition1Activity : BaseFontActivity() {

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LImageUtil.load(context = this, any = Constants.URL_IMG_2, imageView = imageViewItem)
        ViewCompat.setTransitionName(imageViewItem, IV)
        ViewCompat.setTransitionName(textView, TV)
    }
}
