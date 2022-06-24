package vn.loitp.app.activity.customviews.button.lbutton

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_button_l.*
import vn.loitp.app.R

@LogTag("LButtonActivity")
@IsFullScreen(false)
class LButtonActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_l
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = LButtonActivity::class.java.simpleName
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
