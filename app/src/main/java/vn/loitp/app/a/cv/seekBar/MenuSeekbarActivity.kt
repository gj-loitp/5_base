package vn.loitp.app.a.cv.seekBar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_seekbar.*
import vn.loitp.R
import vn.loitp.app.a.cv.seekBar.boxedVerticalSeekBar.BoxedVerticalSeekBarActivity
import vn.loitp.app.a.cv.seekBar.rangeSeekBar.RangeSeekbarActivity
import vn.loitp.app.a.cv.seekBar.seekBar.SeekbarActivity
import vn.loitp.app.a.cv.seekBar.verticalSeekBar.VerticalSeekbarActivity
import vn.loitp.app.a.cv.seekBar.verticalSeekBar2.VerticalSeekBar2Activity

@LogTag("MenuSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSeekbarActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_seekbar
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
            this.tvTitle?.text = MenuSeekbarActivity::class.java.simpleName
        }
        btBoxedVerticalSeekbar.setOnClickListener(this)
        btVerticalSeekBar.setOnClickListener(this)
        btSeekBar.setOnClickListener(this)
        btVerticalSeekBar2.setOnClickListener(this)
        btRangeSeekBar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBoxedVerticalSeekbar ->
                intent =
                    Intent(this, BoxedVerticalSeekBarActivity::class.java)
            btVerticalSeekBar -> intent = Intent(this, VerticalSeekbarActivity::class.java)
            btSeekBar -> intent = Intent(this, SeekbarActivity::class.java)
            btVerticalSeekBar2 -> intent = Intent(this, VerticalSeekBar2Activity::class.java)
            btRangeSeekBar -> intent = Intent(this, RangeSeekbarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
