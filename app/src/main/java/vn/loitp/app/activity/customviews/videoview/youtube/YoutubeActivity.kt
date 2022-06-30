package vn.loitp.app.activity.customviews.videoview.youtube

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
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

        setupViews()
    }

    private fun setupViews() {
        bt0.setSafeOnClickListener {
            LUIUtil.playYoutube(
                activity = this,
                url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender"
            )
        }
        bt1.setSafeOnClickListener {
            LUIUtil.playYoutubeWithId(activity = this, id = "YE7VzlLtp-4")
        }
    }
}
