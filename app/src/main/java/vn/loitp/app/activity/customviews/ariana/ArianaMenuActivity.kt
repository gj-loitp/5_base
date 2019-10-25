package vn.loitp.app.activity.customviews.ariana

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.ariana.iv.ArianaImageViewActivity
import vn.loitp.app.activity.customviews.ariana.tv.ArianaTextViewActivity
import vn.loitp.app.activity.customviews.ariana.vp.ArianaViewPagerActivity

class ArianaMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_ariana_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_ariana_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_ariana_viewpager).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_ariana
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_ariana_imageview -> intent = Intent(activity, ArianaImageViewActivity::class.java)
            R.id.bt_ariana_textview -> intent = Intent(activity, ArianaTextViewActivity::class.java)
            R.id.bt_ariana_viewpager -> intent = Intent(activity, ArianaViewPagerActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
