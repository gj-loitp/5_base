package vn.loitp.app.activity.function.keyboardHeightProvider

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_keyboard_height_provider.*
import vn.loitp.app.R

@LogTag("KeyboardHeightProviderActivity")
@IsFullScreen(false)
class KeyboardHeightProviderActivity : BaseFontActivity() {

    private var keyboardHeightProvider: KeyboardHeightProvider? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_keyboard_height_provider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = KeyboardHeightProviderActivity::class.java.simpleName
        }
        keyboardHeightProvider = KeyboardHeightProvider(this)
        keyboardHeightProvider?.addKeyboardListener(object :
            KeyboardHeightProvider.KeyboardListener {
            override fun onHeightChanged(height: Int) {
                if (height == 0) {
                    layoutKeyboardFake.isVisible = false
                } else {
                    if (layoutKeyboardFake.layoutParams.height != height) {
                        layoutKeyboardFake.layoutParams.height = height
                        layoutKeyboardFake.requestLayout()
                    }
                    layoutKeyboardFake.isVisible = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }

    public override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }
}
