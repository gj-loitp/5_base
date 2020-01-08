package vn.loitp.app.activity.function.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LKeyBoardUtil
import com.views.setSafeOnClickListener
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import kotlinx.android.synthetic.main.activity_keyboard.*
import loitp.basemaster.R

//https://github.com/ParkSangGwon/TedKeyboardObserver
class KeyboardActivity : BaseFontActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TedRxKeyboardObserver(this)
                .listen()
                .subscribe({ isShow ->
                    tv.text = "isShow $isShow"
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

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_keyboard
    }

}
