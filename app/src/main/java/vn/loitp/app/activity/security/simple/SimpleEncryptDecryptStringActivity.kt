package vn.loitp.app.activity.security.simple

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LEncryptionUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_encrypt_decrypt_string.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User

@LogTag("SimpleEncryptDecryptStringActivity")
@IsFullScreen(false)
class SimpleEncryptDecryptStringActivity : BaseFontActivity() {

    private val password = "Loitp@123KawasakiZ1000R"

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_encrypt_decrypt_string
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
            this.tvTitle?.text = SimpleEncryptDecryptStringActivity::class.java.simpleName
        }

        val user = User()
        user.fullName = "Name " + System.currentTimeMillis()
        user.email = "Mail " + System.currentTimeMillis()
        tv0.text = BaseApplication.gson.toJson(user)

        bt0.setSafeOnClickListener { encrypt() }
        bt1.setSafeOnClickListener { decrypt() }

        btEncodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = LEncryptionUtil.encodeBase64(str)
            tvBase64.text = newStr
            btEncodeBase64.visibility = View.GONE
            btDecodeBase64.visibility = View.VISIBLE
        }
        btDecodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = LEncryptionUtil.decodeBase64(str)
            tvBase64.text = newStr
            btEncodeBase64.visibility = View.VISIBLE
            btDecodeBase64.visibility = View.GONE
        }
    }

    private fun encrypt() {
        val str = tv0.text.toString()
        if (str.isEmpty()) {
            showShortInformation("Empty string")
            return
        }
        val newStr = LEncryptionUtil.encrypt(plaintext = str, password = password)
        tv1.text = newStr
        tv0.text = ""
    }

    private fun decrypt() {
        val str = tv1.text.toString()
        if (str.isEmpty()) {
            showShortInformation("Empty string")
            return
        }
        val newStr = LEncryptionUtil.decrypt(cipherText = str, password = password)
        tv1.text = ""
        tv0.text = newStr
    }
}
