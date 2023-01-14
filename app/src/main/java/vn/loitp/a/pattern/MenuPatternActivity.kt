package vn.loitp.a.pattern

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_pattern_menu.*
import vn.loitp.R
import vn.loitp.a.pattern.mvp.MVPActivityFont
import vn.loitp.a.pattern.mvvm.MVVMActivityFont
import vn.loitp.a.pattern.observer.ObserverPatternActivityFont

@LogTag("MenuPatternActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPatternActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_pattern_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
            btObserver -> intent = Intent(this, ObserverPatternActivityFont::class.java)
            btMVVM -> intent = Intent(this, MVVMActivityFont::class.java)
            btMVP -> intent = Intent(this, MVPActivityFont::class.java)
        }
        intent?.let {
            startActivity(intent)
            this.tranIn()
        }
    }
}
