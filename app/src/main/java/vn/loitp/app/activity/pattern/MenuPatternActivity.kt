package vn.loitp.app.activity.pattern

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_pattern_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.MVPActivity
import vn.loitp.app.activity.pattern.mvvm.MVVMActivity
import vn.loitp.app.activity.pattern.observerpattern.ObserverPatternActivity

@LogTag("MenuPatternActivity")
@IsFullScreen(false)
class MenuPatternActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pattern_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
