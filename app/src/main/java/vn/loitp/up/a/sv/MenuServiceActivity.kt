package vn.loitp.up.a.sv

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.sv.demo.DemoServiceActivityFont
import vn.loitp.a.sv.endless.EndlessServiceActivityFont
import vn.loitp.databinding.ASvMenuBinding

@LogTag("MenuServiceActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuServiceActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ASvMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASvMenuBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuServiceActivity::class.java.simpleName
        }
        binding.btDemoService.setOnClickListener(this)
        binding.btEndlessService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btDemoService -> launchActivity(DemoServiceActivityFont::class.java)
            binding.btEndlessService -> launchActivity(EndlessServiceActivityFont::class.java)
        }
    }
}
