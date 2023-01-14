package vn.loitp.a.cv.lDebug

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseModel
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.lDebugView.LComunicateDebug
import com.loitp.views.lDebugView.LDebug
import kotlinx.android.synthetic.main.a_l_debug_view.*
import vn.loitp.R
import vn.loitp.common.Constants.Companion.URL_IMG
import vn.loitp.common.Constants.Companion.URL_IMG_2

@LogTag("LDebugViewActivity")
@IsFullScreen(false)
class LDebugViewActivity : BaseActivityFont(), View.OnClickListener {

    private class User : BaseModel() {
        var avatar: String? = null
        var address: String? = null
        var cover: String? = null
        var email: String? = null
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_l_debug_view
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = LDebugViewActivity::class.java.simpleName
        }
        btStart.setOnClickListener(this)
        btStop.setOnClickListener(this)
        btSendD.setOnClickListener(this)
        btSendI.setOnClickListener(this)
        btSendE.setOnClickListener(this)
        btSendObjectD.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btStart -> {
                LDebug.init(
                    activity = this,
                    data = { intent ->
                        resultOverlay.launch(intent)
                    })
                btStop.isEnabled = true
                btSendD.isEnabled = true
                btSendI.isEnabled = true
                btSendE.isEnabled = true
                btSendObjectD.isEnabled = true
            }
            btStop -> {
                LDebug.stop(this@LDebugViewActivity)
                btStop.isEnabled = false
                btSendD.isEnabled = false
                btSendI.isEnabled = false
                btSendE.isEnabled = false
                btSendObjectD.isEnabled = false
            }
            btSendD -> {
                LDebug.log("Sample d: " + System.currentTimeMillis())
            }
            btSendI -> {
                LDebug.log(
                    LComunicateDebug.MsgFromActivity.TYPE_I,
                    "Sample i: " + System.currentTimeMillis()
                )
            }
            btSendE -> {
                LDebug.log(
                    LComunicateDebug.MsgFromActivity.TYPE_E,
                    "Sample error: " + System.currentTimeMillis()
                )
            }
            btSendObjectD -> {
                val user = User()
                user.avatar = URL_IMG
                user.address = "Address"
                user.cover = URL_IMG_2
                user.email = "www.muathu@gmail.com"
                LDebug.log(user)
            }
        }
    }

    private val resultOverlay =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LDebug.start(this@LDebugViewActivity)
        }
}
