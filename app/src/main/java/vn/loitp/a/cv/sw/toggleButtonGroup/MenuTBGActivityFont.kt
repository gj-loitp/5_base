package vn.loitp.a.cv.sw.toggleButtonGroup

import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_switch_tbg.*
import vn.loitp.R

// https://github.com/nex3z/ToggleButtonGroup
@LogTag("TBGMenuActivity")
@IsFullScreen(false)
class MenuTBGActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_switch_tbg
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
            this.tvTitle?.text = MenuTBGActivityFont::class.java.simpleName
        }

        btnMultiSelectSample.setSafeOnClickListener {
            val intent = Intent(this, TBGMultiSelectActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btnSingleSelectSample.setSafeOnClickListener {
            val intent = Intent(this, TBGSingleSelectActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btnLabelSample.setSafeOnClickListener {
            val intent = Intent(this, TBGFlowLabelActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btnCustomButtonSample.setSafeOnClickListener {
            val intent = Intent(this, TBGCustomButtonActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
    }
}
