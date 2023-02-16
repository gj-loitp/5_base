package vn.loitp.up.a.cv.lDebug

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseModel
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.lDebugView.LComunicateDebug
import com.loitp.views.lDebugView.LDebug
import vn.loitp.databinding.ALDebugViewBinding
import vn.loitp.up.common.Constants.Companion.URL_IMG
import vn.loitp.up.common.Constants.Companion.URL_IMG_2

@LogTag("LDebugViewActivity")
@IsFullScreen(false)
class LDebugViewActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ALDebugViewBinding

    private class User : BaseModel() {
        var avatar: String? = null
        var address: String? = null
        var cover: String? = null
        var email: String? = null
    }

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALDebugViewBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = LDebugViewActivity::class.java.simpleName
        }
        binding.btStart.setOnClickListener(this)
        binding.btStop.setOnClickListener(this)
        binding.btSendD.setOnClickListener(this)
        binding.btSendI.setOnClickListener(this)
        binding.btSendE.setOnClickListener(this)
        binding.btSendObjectD.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btStart -> {
                LDebug.init(
                    activity = this,
                    data = { intent ->
                        resultOverlay.launch(intent)
                    })
                binding.btStop.isEnabled = true
                binding.btSendD.isEnabled = true
                binding.btSendI.isEnabled = true
                binding.btSendE.isEnabled = true
                binding.btSendObjectD.isEnabled = true
            }
            binding.btStop -> {
                LDebug.stop(this@LDebugViewActivity)
                binding.btStop.isEnabled = false
                binding.btSendD.isEnabled = false
                binding.btSendI.isEnabled = false
                binding.btSendE.isEnabled = false
                binding.btSendObjectD.isEnabled = false
            }
            binding.btSendD -> {
                LDebug.log("Sample d: " + System.currentTimeMillis())
            }
            binding.btSendI -> {
                LDebug.log(
                    LComunicateDebug.MsgFromActivity.TYPE_I,
                    "Sample i: " + System.currentTimeMillis()
                )
            }
            binding.btSendE -> {
                LDebug.log(
                    LComunicateDebug.MsgFromActivity.TYPE_E,
                    "Sample error: " + System.currentTimeMillis()
                )
            }
            binding.btSendObjectD -> {
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
