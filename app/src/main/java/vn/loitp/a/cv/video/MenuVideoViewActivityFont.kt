package vn.loitp.a.cv.video

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_video_menu.*
import vn.loitp.R
import vn.loitp.a.cv.video.exo.ExoPlayerActivityFont
import vn.loitp.a.cv.video.exo.ExoPlayerActivityFont2
import vn.loitp.a.cv.video.exo.ExoPlayerActivityFont3
import vn.loitp.a.cv.video.youtube.YoutubeActivityFont

@LogTag("VideoViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuVideoViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_video_menu
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
            this.tvTitle?.text = MenuVideoViewActivityFont::class.java.simpleName
        }

        btExoPlayer2.setSafeOnClickListener {
            launchActivity(cls = ExoPlayerActivityFont::class.java, withAnim = true, data = {
                it.putExtra(
                    Constants.KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
            })
        }
        btExoPlayer2IMA.setSafeOnClickListener {
            launchActivity(cls = ExoPlayerActivityFont::class.java, withAnim = true, data = {
                it.putExtra(
                    Constants.KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
                it.putExtra(Constants.KEY_VIDEO_LINK_IMA_AD, getString(R.string.ad_tag_url))

            })
        }
        bt2.setSafeOnClickListener {
            launchActivity(ExoPlayerActivityFont2::class.java)
        }
        bt3.setSafeOnClickListener {
            launchActivity(ExoPlayerActivityFont3::class.java)
        }
        btYoutube.setSafeOnClickListener {
            launchActivity(YoutubeActivityFont::class.java)
        }
    }

}
