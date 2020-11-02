package vn.loitp.app.activity.animation.overscroll

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_over_scroll.*
import vn.loitp.app.R

@LogTag("OverScrollActivity")
@IsFullScreen(false)
class OverScrollActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_over_scroll
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LUIUtil.setPullLikeIOSVertical(scrollView = nsv)
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.overscroll)
    }

}
