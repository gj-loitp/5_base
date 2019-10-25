package vn.loitp.app.activity.customviews.seekbar

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar.BoxedVerticalSeekBarActivity
import vn.loitp.app.activity.customviews.seekbar.circularseekbar.CircularSeekbarActivity
import vn.loitp.app.activity.customviews.seekbar.seekbar.SeekbarActivity
import vn.loitp.app.activity.customviews.seekbar.verticalseekbar.VerticalSeekbarActivity

class SeekbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_boxed_vertical_seekbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_circularseekbar_seekbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_vertical_seekbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_seekbar).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_seekbar
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_boxed_vertical_seekbar -> intent = Intent(activity, BoxedVerticalSeekBarActivity::class.java)
            R.id.bt_circularseekbar_seekbar -> intent = Intent(activity, CircularSeekbarActivity::class.java)
            R.id.bt_vertical_seekbar -> intent = Intent(activity, VerticalSeekbarActivity::class.java)
            R.id.bt_seekbar -> intent = Intent(activity, SeekbarActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
