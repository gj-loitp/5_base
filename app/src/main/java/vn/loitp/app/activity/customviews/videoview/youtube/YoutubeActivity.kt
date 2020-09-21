package vn.loitp.app.activity.customviews.videoview.youtube

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_video_youtube.*
import vn.loitp.app.R

@LayoutId( R.layout.activity_video_youtube)
@LogTag("YoutubeActivity")
@IsFullScreen(false)
class YoutubeActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener {
            LUIUtil.playYoutube(activity = this, url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender")
        }
        bt1.setOnClickListener {
            LUIUtil.playYoutubeWithId(activity = this, id = "YE7VzlLtp-4")
        }
    }

}
