package vn.loitp.a.sv

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sv_menu.*
import vn.loitp.R
import vn.loitp.a.sv.demo.DemoServiceActivityFont
import vn.loitp.a.sv.endless.EndlessServiceActivityFont

@LogTag("MenuServiceActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuServiceActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sv_menu
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuServiceActivity::class.java.simpleName
        }
        btDemoService.setOnClickListener(this)
        btEndlessService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btDemoService -> launchActivity(DemoServiceActivityFont::class.java)
            btEndlessService -> launchActivity(EndlessServiceActivityFont::class.java)
        }
    }
}
