package vn.loitp.app.activity.customviews.seekBar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_seekbar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.seekBar.boxedVerticalSeekBar.BoxedVerticalSeekBarActivity
import vn.loitp.app.activity.customviews.seekBar.seekBar.SeekbarActivity
import vn.loitp.app.activity.customviews.seekBar.verticalSeekBar.VerticalSeekbarActivity

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
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuSeekbarActivity::class.java.simpleName
        }
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
