package vn.loitp.app.activity.security.simple

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LEncryptionUtil
import kotlinx.android.synthetic.main.activity_encrypt_decrypt_string.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User
import vn.loitp.app.app.LApplication

class SimpleEncryptDecryptStringActivity : BaseFontActivity() {

    private val password = "Loitp@123KawasakiZ1000R"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = User()
        user.fullName = "Name " + System.currentTimeMillis()
        user.email = "Mail " + System.currentTimeMillis()
        tv0.text = LApplication.gson.toJson(user)

        bt0.setOnClickListener { encrypt() }
        bt1.setOnClickListener { decrypt() }

        btEncodeBase64.setOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = LEncryptionUtil.encodeBase64(str)
            tvBase64.text = newStr
            btEncodeBase64.visibility = View.GONE
            btDecodeBase64.visibility = View.VISIBLE
        }
        btDecodeBase64.setOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = LEncryptionUtil.decodeBase64(str)
            tvBase64.text = newStr
            btEncodeBase64.visibility = View.VISIBLE
            btDecodeBase64.visibility = View.GONE
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_encrypt_decrypt_string
    }

    private fun encrypt() {
        val str = tv0.text.toString()
        if (str.isEmpty()) {
            showShort("Empty string")
            return
        }
        val newStr = LEncryptionUtil.encrypt(str, password)
        tv1.text = newStr
        tv0.text = ""
    }

    private fun decrypt() {
        val str = tv1.text.toString()
        if (str.isEmpty()) {
            showShort("Empty string")
            return
        }
        val newStr = LEncryptionUtil.decrypt(str, password)
        tv1.text = ""
        tv0.text = newStr
    }

}
