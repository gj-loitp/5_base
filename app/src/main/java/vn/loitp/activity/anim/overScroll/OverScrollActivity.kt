package vn.loitp.activity.anim.overScroll

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_over_scroll.*
import vn.loitp.R

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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = OverScrollActivity::class.java.simpleName
        }

        LUIUtil.setPullLikeIOSVertical(scrollView = nsv)
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.overscroll)
    }
}
