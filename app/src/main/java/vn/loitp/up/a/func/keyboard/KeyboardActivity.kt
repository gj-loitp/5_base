package vn.loitp.up.a.func.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideKeyboard
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showKeyboard
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import vn.loitp.R
import vn.loitp.databinding.AFuncKeyboardBinding

// https://github.com/ParkSangGwon/TedKeyboardObserver

@LogTag("KeyboardActivity")
@IsFullScreen(false)
class KeyboardActivity : BaseActivityFont() {

    private lateinit var binding: AFuncKeyboardBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = KeyboardActivity::class.java.simpleName
        }
        TedRxKeyboardObserver(this)
            .listen()
            .subscribe({ isShow ->
                binding.textView.text = "isShow $isShow"
            }, { throwable -> throwable.printStackTrace() })

        binding.btShow.setSafeOnClickListener {
            this.showKeyboard()
        }
        binding.btHide.setSafeOnClickListener {
            this.hideKeyboard()
        }
    }
}
