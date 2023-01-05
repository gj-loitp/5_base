package vn.loitp.a.cv.scratchView

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_scratch_view.*
import vn.loitp.R
import vn.loitp.a.cv.scratchView.scratchViewIv.ScratchViewImageActivityFont
import vn.loitp.a.cv.scratchView.scratchViewTv.ScratchViewTextActivityFont

@LogTag("ScratchViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuScratchViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_scratch_view
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
            this.tvTitle?.text = MenuScratchViewActivityFont::class.java.simpleName
        }
        btScratchViewImage.setSafeOnClickListener {
            launchActivity(ScratchViewImageActivityFont::class.java)
        }
        btScratchViewText.setSafeOnClickListener {
            launchActivity(ScratchViewTextActivityFont::class.java)
        }
    }
}
