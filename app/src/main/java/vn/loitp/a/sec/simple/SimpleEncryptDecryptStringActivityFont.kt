package vn.loitp.a.sec.simple

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.*
import kotlinx.android.synthetic.main.a_encrypt_decrypt_string.*
import vn.loitp.R
import vn.loitp.a.pattern.mvp.User

@LogTag("SimpleEncryptDecryptStringActivity")
@IsFullScreen(false)
class SimpleEncryptDecryptStringActivityFont : BaseActivityFont() {

    private val password = "Loitp@123KawasakiZ1000R"

    override fun setLayoutResourceId(): Int {
        return R.layout.a_encrypt_decrypt_string
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SimpleEncryptDecryptStringActivityFont::class.java.simpleName
        }

        val user = User()
        user.fullName = "Name " + System.currentTimeMillis()
        user.email = "Mail " + System.currentTimeMillis()
        tv0.text = BaseApplication.gson.toJson(user)

        bt0.setSafeOnClickListener { encrypt() }
        bt1.setSafeOnClickListener { decrypt() }

        btEncodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = str.encodeBase64()
            tvBase64.text = newStr
            btEncodeBase64.visibility = View.GONE
            btDecodeBase64.visibility = View.VISIBLE
        }
        btDecodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = str.decodeBase64()
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
        val newStr = str.encrypt(password = password)
        tv1.text = newStr
        tv0.text = ""
    }

    private fun decrypt() {
        val str = tv1.text.toString()
        if (str.isEmpty()) {
            showShortInformation("Empty string")
            return
        }
        val newStr = str.decrypt(password = password)
        tv1.text = ""
        tv0.text = newStr
    }
}
