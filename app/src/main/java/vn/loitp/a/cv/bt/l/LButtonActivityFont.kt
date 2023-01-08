package vn.loitp.a.cv.bt.l

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_l_button.*
import vn.loitp.R

@LogTag("LButtonActivity")
@IsFullScreen(false)
class LButtonActivityFont : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_l_button
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = LButtonActivityFont::class.java.simpleName
        }

        bt0.setOnClickListener(this)

        bt1.setPressedDrawable(R.drawable.l_circle_color_primary)
        bt1.setOnClickListener(this)

        bt2.setPressedDrawable(R.drawable.l_bt_color_primary)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            bt0, bt1, bt2 ->
                showShortInformation(msg = getString(R.string.click))
        }
    }
}
