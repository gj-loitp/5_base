package vn.loitp.app.activity.customviews.videoview.youtube

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_video_youtube.*
import vn.loitp.app.R

@LogTag("YoutubeActivity")
@IsFullScreen(false)
class YoutubeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_youtube
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setSafeOnClickListener {
            LUIUtil.playYoutube(activity = this, url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender")
        }
        bt1.setSafeOnClickListener {
            LUIUtil.playYoutubeWithId(activity = this, id = "YE7VzlLtp-4")
        }
    }

}
