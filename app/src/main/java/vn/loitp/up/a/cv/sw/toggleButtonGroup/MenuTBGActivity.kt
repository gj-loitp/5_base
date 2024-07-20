package vn.loitp.up.a.cv.sw.toggleButtonGroup

import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import vn.loitp.R
import vn.loitp.databinding.AMenuSwitchTbgBinding

// https://github.com/nex3z/ToggleButtonGroup
@LogTag("TBGMenuActivity")
@IsFullScreen(false)
class MenuTBGActivity : BaseActivityFont() {

    private lateinit var binding: AMenuSwitchTbgBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuSwitchTbgBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuTBGActivity::class.java.simpleName
        }

        binding.btnMultiSelectSample.setSafeOnClickListener {
            val intent = Intent(this, TBGMultiSelectActivity::class.java)
            startActivity(intent)
            this.tranIn()
        }
        binding.btnSingleSelectSample.setSafeOnClickListener {
            val intent = Intent(this, TBGSingleSelectActivity::class.java)
            startActivity(intent)
            this.tranIn()
        }
        binding.btnLabelSample.setSafeOnClickListener {
            val intent = Intent(this, TBGFlowLabelActivity::class.java)
            startActivity(intent)
            this.tranIn()
        }
        binding.btnCustomButtonSample.setSafeOnClickListener {
            val intent = Intent(this, TBGCustomButtonActivity::class.java)
            startActivity(intent)
            this.tranIn()
        }
    }
}
