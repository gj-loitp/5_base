package vn.loitp.app.activity.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSoundUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_demo_sound.*
import vn.loitp.R

@LogTag("SoundActivity")
@IsFullScreen(false)
class SoundActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_sound
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
            this.tvTitle?.text = SoundActivity::class.java.simpleName
        }
        btPlay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btPlay -> LSoundUtil.startMusicFromAsset(fileName = "ting.ogg")
        }
    }
}
