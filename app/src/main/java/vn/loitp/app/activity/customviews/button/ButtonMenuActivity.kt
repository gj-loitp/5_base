package vn.loitp.app.activity.customviews.button

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_button_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.button.autosizebutton.AutoSizeButtonActivity
import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity
import vn.loitp.app.activity.customviews.button.goodview.GoodViewActivity
import vn.loitp.app.activity.customviews.button.lbutton.LButtonActivity
import vn.loitp.app.activity.customviews.button.qbutton.QButtonActivity
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity

@LogTag("ButtonMenuActivity")
@IsFullScreen(false)
class ButtonMenuActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btShineButton.setOnClickListener(this)
        btCircularImageClick.setOnClickListener(this)
        btGoodView.setOnClickListener(this)
        btlButton.setOnClickListener(this)
        btAutoSizeButton.setOnClickListener(this)
        btQButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btShineButton -> intent = Intent(this, ShineButtonActivity::class.java)
            btCircularImageClick -> intent = Intent(this, CircularImageClickActivity::class.java)
            btGoodView -> intent = Intent(this, GoodViewActivity::class.java)
            btlButton -> intent = Intent(this, LButtonActivity::class.java)
            btAutoSizeButton -> intent = Intent(this, AutoSizeButtonActivity::class.java)
            btQButton -> intent = Intent(this, QButtonActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
