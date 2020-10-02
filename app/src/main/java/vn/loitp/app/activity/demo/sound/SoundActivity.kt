package vn.loitp.app.activity.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSoundUtil
import kotlinx.android.synthetic.main.activity_demo_sound.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_sound)
@LogTag("SoundActivity")
@IsFullScreen(false)
class SoundActivity : BaseFontActivity(), OnClickListener {
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
