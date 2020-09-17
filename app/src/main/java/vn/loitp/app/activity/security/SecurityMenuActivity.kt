package vn.loitp.app.activity.security

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_security_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.security.simple.SimpleEncryptDecryptStringActivity

@LayoutId(R.layout.activity_security_menu)
class SecurityMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt0.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt0 -> intent = Intent(activity, SimpleEncryptDecryptStringActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
