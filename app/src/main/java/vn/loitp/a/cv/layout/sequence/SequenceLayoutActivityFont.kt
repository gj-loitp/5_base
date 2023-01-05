package vn.loitp.a.cv.layout.sequence

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_sequence.*
import vn.loitp.R

@LogTag("SequenceLayoutActivity")
@IsFullScreen(false)
// https://github.com/transferwise/sequence-layout
class SequenceLayoutActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_sequence
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
            this.tvTitle?.text = SequenceLayoutActivityFont::class.java.simpleName
        }
    }
}
