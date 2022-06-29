package vn.loitp.app.activity.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSoundUtil
import kotlinx.android.synthetic.main.activity_demo_sound.*
import vn.loitp.app.R

@LogTag("SoundActivity")
@IsFullScreen(false)
class SoundActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_sound
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPlay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btPlay -> LSoundUtil.startMusicFromAsset(fileName = "ting.ogg")
        }
    }
}
