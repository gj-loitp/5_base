package vn.loitp.app.activity.function.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LKeyBoardUtil
import com.loitpcore.core.utilities.LUIUtil
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
            this.viewShadow?.isVisible = true
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
