package vn.loitp.app.activity.animation.basictransition

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_basic_transition_1.*
import vn.loitp.app.R

class BasicTransition1Activity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(context = activity, url = Constants.URL_IMG_2, imageView = imageViewItem)
        ViewCompat.setTransitionName(imageViewItem, IV)
        ViewCompat.setTransitionName(textView, TV)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_basic_transition_1
    }

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }
}
