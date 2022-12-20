package vn.loitp.app.activity.service

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_service.*
import vn.loitp.app.R
import vn.loitp.app.activity.service.demoService.DemoServiceActivity
import vn.loitp.app.activity.service.endlessService.EndlessServiceActivity

@LogTag("MenuServiceActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_service
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
            this.tvTitle?.text = MenuServiceActivity::class.java.simpleName
        }
        btDemoService.setOnClickListener(this)
        btEndlessService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btDemoService -> launchActivity(DemoServiceActivity::class.java)
            btEndlessService -> launchActivity(EndlessServiceActivity::class.java)
        }
    }
}
