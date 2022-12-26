package vn.loitp.a.func.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LKeyBoardUtil
import com.loitp.core.utilities.LUIUtil
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import kotlinx.android.synthetic.main.a_func_keyboard.*
import vn.loitp.R

// https://github.com/ParkSangGwon/TedKeyboardObserver

@LogTag("KeyboardActivity")
@IsFullScreen(false)
class KeyboardActivity : BaseFontActivity() {

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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
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
            LKeyBoardUtil.show(this)
        }
        btHide.setSafeOnClickListener {
            LKeyBoardUtil.hide(this)
        }
    }
}
