package vn.loitp.a.cv.sb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sb_menu.*
import vn.loitp.R
import vn.loitp.a.cv.sb.boxedVertical.BoxedVerticalSeekBarActivity
import vn.loitp.a.cv.sb.range.RangeSeekbarActivity
import vn.loitp.a.cv.sb.rubberPicker.RubberPickerActivity
import vn.loitp.a.cv.sb.sb.SeekbarActivity
import vn.loitp.a.cv.sb.vertical.VerticalSeekbarActivity
import vn.loitp.a.cv.sb.vertical2.VerticalSeekBar2Activity

@LogTag("MenuSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSeekbarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb_menu
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
        btBoxedVerticalSeekbar.setSafeOnClickListener {
            launchActivity(BoxedVerticalSeekBarActivity::class.java)
        }
        btVerticalSeekBar.setSafeOnClickListener {
            launchActivity(VerticalSeekbarActivity::class.java)
        }
        btSeekBar.setSafeOnClickListener {
            launchActivity(SeekbarActivity::class.java)
        }
        btVerticalSeekBar2.setSafeOnClickListener {
            launchActivity(VerticalSeekBar2Activity::class.java)
        }
        btRangeSeekBar.setSafeOnClickListener {
            launchActivity(RangeSeekbarActivity::class.java)
        }
        btRubberPicker.setSafeOnClickListener {
            launchActivity(RubberPickerActivity::class.java)
        }
    }
}
