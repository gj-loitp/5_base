package vn.loitp.up.a.sv.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ASvDemoBinding

@LogTag("DemoServiceActivity")
@IsFullScreen(false)
class DemoServiceActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ASvDemoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASvDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = DemoServiceActivity::class.java.simpleName
        }
        binding.btStartService.setOnClickListener(this)
        binding.btStopService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btStartService -> {
                startService(Intent(applicationContext, DemoService::class.java))
            }
            binding.btStopService -> {
                stopService(Intent(applicationContext, DemoService::class.java))
            }
        }
    }
}
