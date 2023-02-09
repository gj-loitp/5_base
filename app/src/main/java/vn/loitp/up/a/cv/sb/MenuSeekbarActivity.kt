package vn.loitp.up.a.cv.sb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.cv.sb.boxedVertical.BoxedVerticalSeekBarActivityFont
import vn.loitp.a.cv.sb.range.RangeSeekbarActivityFont
import vn.loitp.a.cv.sb.rubberPicker.RubberPickerActivityFont
import vn.loitp.databinding.ASbMenuBinding
import vn.loitp.up.a.cv.sb.sb.SeekbarActivity
import vn.loitp.up.a.cv.sb.vertical.VerticalSeekbarActivity
import vn.loitp.up.a.cv.sb.vertical2.VerticalSeekBar2Activity

@LogTag("MenuSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSeekbarActivity : BaseActivityFont() {

    private lateinit var binding: ASbMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASbMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSeekbarActivity::class.java.simpleName
        }
        binding.btBoxedVerticalSeekbar.setSafeOnClickListener {
            launchActivity(BoxedVerticalSeekBarActivityFont::class.java)
        }
        binding.btVerticalSeekBar.setSafeOnClickListener {
            launchActivity(VerticalSeekbarActivity::class.java)
        }
        binding.btSeekBar.setSafeOnClickListener {
            launchActivity(SeekbarActivity::class.java)
        }
        binding.btVerticalSeekBar2.setSafeOnClickListener {
            launchActivity(VerticalSeekBar2Activity::class.java)
        }
        binding.btRangeSeekBar.setSafeOnClickListener {
            launchActivity(RangeSeekbarActivityFont::class.java)
        }
        binding.btRubberPicker.setSafeOnClickListener {
            launchActivity(RubberPickerActivityFont::class.java)
        }
    }
}
