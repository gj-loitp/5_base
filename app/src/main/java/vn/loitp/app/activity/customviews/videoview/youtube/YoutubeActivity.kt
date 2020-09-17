package vn.loitp.app.activity.customviews.videoview.youtube

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_video_youtube.*
import vn.loitp.app.R

@LayoutId( R.layout.activity_video_youtube)
class YoutubeActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener {
            LUIUtil.playYoutube(activity = activity, url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender")
        }
        bt1.setOnClickListener {
            LUIUtil.playYoutubeWithId(activity = activity, id = "YE7VzlLtp-4")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
