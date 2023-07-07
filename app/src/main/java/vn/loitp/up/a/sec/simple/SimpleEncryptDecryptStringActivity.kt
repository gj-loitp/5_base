package vn.loitp.up.a.sec.simple

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import kotlinx.android.synthetic.main.a_encrypt_decrypt_string.*
import vn.loitp.R
import vn.loitp.databinding.AEncryptDecryptStringBinding
import vn.loitp.up.a.pattern.mvp.User

@LogTag("SimpleEncryptDecryptStringActivity")
@IsFullScreen(false)
class SimpleEncryptDecryptStringActivity : BaseActivityFont() {

    private val password = "Loitp@123KawasakiZ1000R"
    private lateinit var binding: AEncryptDecryptStringBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEncryptDecryptStringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SimpleEncryptDecryptStringActivity::class.java.simpleName
        }

        val user = User()
        user.fullName = "Name " + System.currentTimeMillis()
        user.email = "Mail " + System.currentTimeMillis()
        binding.tv0.text = BaseApplication.gson.toJson(user)

        binding.bt0.setSafeOnClickListener { encrypt() }
        binding.bt1.setSafeOnClickListener { decrypt() }

        binding.btEncodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = str.encodeBase64()
            binding.tvBase64.text = newStr
            binding.btEncodeBase64.visibility = View.GONE
            binding.btDecodeBase64.visibility = View.VISIBLE
        }
        binding.btDecodeBase64.setSafeOnClickListener {
            val str = tvBase64.text.toString()
            val newStr = str.decodeBase64()
            binding.tvBase64.text = newStr
            binding.btEncodeBase64.visibility = View.VISIBLE
            binding.btDecodeBase64.visibility = View.GONE
        }
    }

    private fun encrypt() {
        val str = binding.tv0.text.toString()
        if (str.isEmpty()) {
            showShortInformation("Empty string")
            return
        }
        val newStr = str.encrypt(password = password)
        binding.tv1.text = newStr
        binding.tv0.text = ""
    }

    private fun decrypt() {
        val str = binding.tv1.text.toString()
        if (str.isEmpty()) {
            showShortInformation("Empty string")
            return
        }
        val newStr = str.decrypt(password = password)
        binding.tv1.text = ""
        binding.tv0.text = newStr
    }
}
