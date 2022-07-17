package vn.loitp.app.activity.customviews.seekBar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_seekbar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.seekBar.boxedVerticalSeekBar.BoxedVerticalSeekBarActivity
import vn.loitp.app.activity.customviews.seekBar.seekBar.SeekbarActivity
import vn.loitp.app.activity.customviews.seekBar.verticalSeekBar.VerticalSeekbarActivity

@LogTag("SeekbarMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class SeekbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btBoxedVerticalSeekbar.setOnClickListener(this)
        btVerticalSeekBar.setOnClickListener(this)
        btSeekBar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBoxedVerticalSeekbar ->
                intent =
                    Intent(this, BoxedVerticalSeekBarActivity::class.java)
            btVerticalSeekBar -> intent = Intent(this, VerticalSeekbarActivity::class.java)
            btSeekBar -> intent = Intent(this, SeekbarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
