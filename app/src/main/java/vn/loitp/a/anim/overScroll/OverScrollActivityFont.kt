package vn.loitp.a.anim.overScroll

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setPullLikeIOSVertical
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_animation_over_scroll.*
import vn.loitp.R

@LogTag("OverScrollActivity")
@IsFullScreen(false)
class OverScrollActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_over_scroll
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
            this.tvTitle?.text = OverScrollActivityFont::class.java.simpleName
        }

        nsv.setPullLikeIOSVertical()
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.overscroll)
    }
}
