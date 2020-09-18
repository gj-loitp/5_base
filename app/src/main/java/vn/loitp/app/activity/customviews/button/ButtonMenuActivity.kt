package vn.loitp.app.activity.customviews.button

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_button_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.button.autosizebutton.AutoSizeButtonActivity
import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity
import vn.loitp.app.activity.customviews.button.goodview.GoodViewActivity
import vn.loitp.app.activity.customviews.button.lbutton.LButtonActivity
import vn.loitp.app.activity.customviews.button.qbutton.QButtonActivity
import vn.loitp.app.activity.customviews.button.roundedbutton.RoundedButtonActivity
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity

@LayoutId(R.layout.activity_button_menu)
@LogTag("ButtonMenuActivity")
class ButtonMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LUIUtil.setPullLikeIOSVertical(nestedScrollView)

        btShineButton.setOnClickListener(this)
        btCircularImageClick.setOnClickListener(this)
        btGoodView.setOnClickListener(this)
        btlButton.setOnClickListener(this)
        btAutoSizeButton.setOnClickListener(this)
        btRoundedButton.setOnClickListener(this)
        btQButton.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btShineButton -> intent = Intent(activity, ShineButtonActivity::class.java)
            btCircularImageClick -> intent = Intent(activity, CircularImageClickActivity::class.java)
            btGoodView -> intent = Intent(activity, GoodViewActivity::class.java)
            btlButton -> intent = Intent(activity, LButtonActivity::class.java)
            btAutoSizeButton -> intent = Intent(activity, AutoSizeButtonActivity::class.java)
            btRoundedButton -> intent = Intent(activity, RoundedButtonActivity::class.java)
            btQButton -> intent = Intent(activity, QButtonActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
