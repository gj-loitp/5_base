package vn.loitp.a.cv.sb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sb_menu.*
import vn.loitp.R
import vn.loitp.a.cv.sb.boxedVertical.BoxedVerticalSeekBarActivityFont
import vn.loitp.a.cv.sb.range.RangeSeekbarActivityFont
import vn.loitp.a.cv.sb.rubberPicker.RubberPickerActivityFont
import vn.loitp.a.cv.sb.sb.SeekbarActivityFont
import vn.loitp.a.cv.sb.vertical.VerticalSeekbarActivityFont
import vn.loitp.a.cv.sb.vertical2.VerticalSeekBar2ActivityFont

@LogTag("MenuSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSeekbarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb_menu
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
            this.tvTitle?.text = MenuSeekbarActivityFont::class.java.simpleName
        }
        btBoxedVerticalSeekbar.setSafeOnClickListener {
            launchActivity(BoxedVerticalSeekBarActivityFont::class.java)
        }
        btVerticalSeekBar.setSafeOnClickListener {
            launchActivity(VerticalSeekbarActivityFont::class.java)
        }
        btSeekBar.setSafeOnClickListener {
            launchActivity(SeekbarActivityFont::class.java)
        }
        btVerticalSeekBar2.setSafeOnClickListener {
            launchActivity(VerticalSeekBar2ActivityFont::class.java)
        }
        btRangeSeekBar.setSafeOnClickListener {
            launchActivity(RangeSeekbarActivityFont::class.java)
        }
        btRubberPicker.setSafeOnClickListener {
            launchActivity(RubberPickerActivityFont::class.java)
        }
    }
}
