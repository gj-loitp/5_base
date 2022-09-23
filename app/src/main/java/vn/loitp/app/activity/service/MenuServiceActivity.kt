package vn.loitp.app.activity.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuServiceActivity::class.java.simpleName
        }
        btDemoService.setOnClickListener(this)
        btEndlessService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDemoService -> intent = Intent(this, DemoServiceActivity::class.java)
            btEndlessService -> intent = Intent(this, EndlessServiceActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
