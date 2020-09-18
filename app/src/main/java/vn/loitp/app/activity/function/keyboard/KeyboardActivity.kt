package vn.loitp.app.activity.function.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LKeyBoardUtil
import com.views.setSafeOnClickListener
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import kotlinx.android.synthetic.main.activity_func_keyboard.*
import vn.loitp.app.R

//https://github.com/ParkSangGwon/TedKeyboardObserver

@LayoutId(R.layout.activity_func_keyboard)
@LogTag("KeyboardActivity")
class KeyboardActivity : BaseFontActivity() {

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TedRxKeyboardObserver(this)
                .listen()
                .subscribe({ isShow ->
                    textView.text = "isShow $isShow"
                }, { throwable -> throwable.printStackTrace() })

        btShow.setSafeOnClickListener {
            LKeyBoardUtil.show(activity)
        }
        btHide.setSafeOnClickListener {
            LKeyBoardUtil.hide(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
