package vn.loitp.app.activity.pattern

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_pattern.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.MVPActivity
import vn.loitp.app.activity.pattern.mvvm.MVVMActivity
import vn.loitp.app.activity.pattern.observerPattern.ObserverPatternActivity

@LogTag("MenuPatternActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPatternActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_pattern
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuPatternActivity::class.java.simpleName
        }
        btObserver.setOnClickListener(this)
        btMVVM.setOnClickListener(this)
        btMVP.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btObserver -> intent = Intent(this, ObserverPatternActivity::class.java)
            btMVVM -> intent = Intent(this, MVVMActivity::class.java)
            btMVP -> intent = Intent(this, MVPActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
