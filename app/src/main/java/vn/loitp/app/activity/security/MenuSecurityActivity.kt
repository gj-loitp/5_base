package vn.loitp.app.activity.security

import android.content.Intent
import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_security_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.security.simple.SimpleEncryptDecryptStringActivity

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_security_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        bt0.setSafeOnClickListener {
            val intent = Intent(this, SimpleEncryptDecryptStringActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
