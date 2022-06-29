package vn.loitp.app.activity.function.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LKeyBoardUtil
import com.loitpcore.views.setSafeOnClickListener
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import kotlinx.android.synthetic.main.activity_func_keyboard.*
import vn.loitp.app.R

// https://github.com/ParkSangGwon/TedKeyboardObserver

@LogTag("KeyboardActivity")
@IsFullScreen(false)
class KeyboardActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_keyboard
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
