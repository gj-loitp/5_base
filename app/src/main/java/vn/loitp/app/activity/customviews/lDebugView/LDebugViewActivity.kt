package vn.loitp.app.activity.customviews.lDebugView

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.base.BaseModel
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.lDebugView.LComunicateDebug
import com.loitpcore.views.lDebugView.LDebug
import kotlinx.android.synthetic.main.activity_l_debug_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants.Companion.URL_IMG
import vn.loitp.app.common.Constants.Companion.URL_IMG_2

@LogTag("LDebugViewActivity")
@IsFullScreen(false)
class LDebugViewActivity : BaseFontActivity(), View.OnClickListener {

    private class User : BaseModel() {
        var avatar: String? = null
        var address: String? = null
        var cover: String? = null
        var email: String? = null
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_debug_view
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

    //TODO fix onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        LDebug.checkPermission(activity = this, requestCode = requestCode, resultCode = resultCode)
    }

    override fun onClick(v: View) {
        when (v) {
            btStart -> {
                LDebug.init(this)
                btStop.isEnabled = true
                btSendD.isEnabled = true
                btSendI.isEnabled = true
                btSendE.isEnabled = true
                btSendObjectD.isEnabled = true
            }
            btStop -> {
                LDebug.stop()
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
}
