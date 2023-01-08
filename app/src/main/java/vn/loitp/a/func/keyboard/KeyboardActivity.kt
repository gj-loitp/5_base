package vn.loitp.a.func.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.hideKeyboard
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showKeyboard
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import kotlinx.android.synthetic.main.a_func_keyboard.*
import vn.loitp.R

// https://github.com/ParkSangGwon/TedKeyboardObserver

@LogTag("KeyboardActivity")
@IsFullScreen(false)
class KeyboardActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_keyboard
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
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
                textView.text = "isShow $isShow"
            }, { throwable -> throwable.printStackTrace() })

        btShow.setSafeOnClickListener {
            this.showKeyboard()
        }
        btHide.setSafeOnClickListener {
            this.hideKeyboard()
        }
    }
}
