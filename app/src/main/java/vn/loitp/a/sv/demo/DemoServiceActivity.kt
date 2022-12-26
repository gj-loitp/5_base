package vn.loitp.a.sv.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sv_demo.*
import vn.loitp.R

@LogTag("DemoServiceActivity")
@IsFullScreen(false)
class DemoServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sv_demo
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
//                    onBackPressed()
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = DemoServiceActivity::class.java.simpleName
        }
        btStartService.setOnClickListener(this)
        btStopService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btStartService -> {
                startService(Intent(applicationContext, DemoService::class.java))
            }
            btStopService -> {
                stopService(Intent(applicationContext, DemoService::class.java))
            }
        }
    }
}
