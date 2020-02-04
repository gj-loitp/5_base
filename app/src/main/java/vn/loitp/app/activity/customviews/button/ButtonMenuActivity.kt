package vn.loitp.app.activity.customviews.button

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_button_menu.*

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.button.autosizebutton.AutoSizeButtonActivity
import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity
import vn.loitp.app.activity.customviews.button.goodview.GoodViewActivity
import vn.loitp.app.activity.customviews.button.lbutton.LButtonActivity
import vn.loitp.app.activity.customviews.button.qbutton.QButtonActivity
import vn.loitp.app.activity.customviews.button.roundedbutton.RoundedButtonActivity
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity

class ButtonMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_shine_button).setOnClickListener(this)
        findViewById<View>(R.id.bt_circular_image_click).setOnClickListener(this)
        findViewById<View>(R.id.bt_goodview).setOnClickListener(this)
        findViewById<View>(R.id.bt_l_button).setOnClickListener(this)
        findViewById<View>(R.id.bt_auto_size_button).setOnClickListener(this)
        findViewById<View>(R.id.bt_rounded_button).setOnClickListener(this)
        btQButton.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_shine_button -> intent = Intent(activity, ShineButtonActivity::class.java)
            R.id.bt_circular_image_click -> intent = Intent(activity, CircularImageClickActivity::class.java)
            R.id.bt_goodview -> intent = Intent(activity, GoodViewActivity::class.java)
            R.id.bt_l_button -> intent = Intent(activity, LButtonActivity::class.java)
            R.id.bt_auto_size_button -> intent = Intent(activity, AutoSizeButtonActivity::class.java)
            R.id.bt_rounded_button -> intent = Intent(activity, RoundedButtonActivity::class.java)
            R.id.btQButton -> intent = Intent(activity, QButtonActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
