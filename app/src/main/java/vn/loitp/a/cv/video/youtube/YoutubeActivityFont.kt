package vn.loitp.a.cv.video.youtube

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.playYoutube
import com.loitp.core.ext.playYoutubeWithId
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_video_youtube.*
import vn.loitp.R

@LogTag("YoutubeActivity")
@IsFullScreen(false)
class YoutubeActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_video_youtube
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
            this.tvTitle?.text = YoutubeActivityFont::class.java.simpleName
        }
        bt0.setSafeOnClickListener {
            this.playYoutube(
                url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender"
            )
        }
        bt1.setSafeOnClickListener {
            this.playYoutubeWithId(id = "YE7VzlLtp-4")
        }
    }
}
