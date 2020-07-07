package vn.loitp.app.activity.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LSoundUtil
import kotlinx.android.synthetic.main.activity_demo_sound.*

import vn.loitp.app.R

class SoundActivity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPlay.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_sound
    }

    override fun onClick(v: View) {
        when (v) {
            btPlay -> LSoundUtil.startMusicFromAsset(activity, "ting.ogg")
        }
    }
}
