package vn.loitp.app.activity.security.simple

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_encrypt_decrypt_string.*
import loitp.basemaster.R
import vn.loitp.app.activity.security.simple.Encryption.decrypt

class SimpleEncryptDecryptStringActivity : BaseFontActivity() {

    private val password = "Loitp@123KawasakiZ1000R"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt0.setOnClickListener { encrypt() }
        bt1.setOnClickListener { decrypt() }
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
        val newStr = Encryption.encrypt(str, password)
        tv1.text = newStr
        tv0.text = ""
    }

    private fun decrypt() {
        val str = tv1.text.toString()
        if (str.isEmpty()) {
            showShort("Empty string")
            return
        }
        val newStr = Encryption.decrypt(str, password)
        tv1.text = ""
        tv0.text = newStr
    }

}
