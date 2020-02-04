package vn.loitp.app.activity.customviews.videoview.youtube

import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil

import loitp.basemaster.R

class YoutubeActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_0).setOnClickListener { _ -> LUIUtil.playYoutube(activity, "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender") }
        findViewById<View>(R.id.bt_1).setOnClickListener { _ -> LUIUtil.playYoutubeWithId(activity, "YE7VzlLtp-4") }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_youtube
    }
}
