package vn.loitp.app.activity.customviews.seekbar

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_seekbar_menu.*

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar.BoxedVerticalSeekBarActivity
import vn.loitp.app.activity.customviews.seekbar.circularseekbar.CircularSeekbarActivity
import vn.loitp.app.activity.customviews.seekbar.seekbar.SeekbarActivity
import vn.loitp.app.activity.customviews.seekbar.verticalseekbar.VerticalSeekbarActivity

class SeekbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btBoxedVerticalSeekbar.setOnClickListener(this)
        btCircularSeekbar.setOnClickListener(this)
        btVerticalSeekBar.setOnClickListener(this)
        btSeekBar.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBoxedVerticalSeekbar -> intent = Intent(activity, BoxedVerticalSeekBarActivity::class.java)
            btCircularSeekbar -> intent = Intent(activity, CircularSeekbarActivity::class.java)
            btVerticalSeekBar -> intent = Intent(activity, VerticalSeekbarActivity::class.java)
            btSeekBar -> intent = Intent(activity, SeekbarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
