package vn.loitp.a.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.startMusicFromAsset
import kotlinx.android.synthetic.main.a_demo_sound.*
import vn.loitp.R

@LogTag("SoundActivity")
@IsFullScreen(false)
class SoundActivityFont : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_sound
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
            this.tvTitle?.text = SoundActivityFont::class.java.simpleName
        }
        btPlay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btPlay -> this.startMusicFromAsset(fileName = "ting.ogg")
        }
    }
}
