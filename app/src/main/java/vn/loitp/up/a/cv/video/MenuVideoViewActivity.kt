package vn.loitp.up.a.cv.video

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.KEY_VIDEO_LINK_IMA_AD
import com.loitp.core.common.KEY_VIDEO_LINK_PLAY
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVideoMenuBinding
import vn.loitp.up.a.cv.video.exo.ExoPlayerActivity
import vn.loitp.up.a.cv.video.exo.ExoPlayerActivity2
import vn.loitp.up.a.cv.video.exo.ExoPlayerActivity3
import vn.loitp.up.a.cv.video.youtube.YoutubeActivity

@LogTag("VideoViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuVideoViewActivity : BaseActivityFont() {

    private lateinit var binding: AVideoMenuBinding

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_video_menu
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVideoMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuVideoViewActivity::class.java.simpleName
        }

        binding.btExoPlayer2.setSafeOnClickListener {
            launchActivity(cls = ExoPlayerActivity::class.java, withAnim = true, data = {
                it.putExtra(
                    KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
            })
        }
        binding.btExoPlayer2IMA.setSafeOnClickListener {
            launchActivity(cls = ExoPlayerActivity::class.java, withAnim = true, data = {
                it.putExtra(
                    KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
                it.putExtra(KEY_VIDEO_LINK_IMA_AD, getString(R.string.ad_tag_url))

            })
        }
        binding.bt2.setSafeOnClickListener {
            launchActivity(ExoPlayerActivity2::class.java)
        }
        binding.bt3.setSafeOnClickListener {
            launchActivity(ExoPlayerActivity3::class.java)
        }
        binding.btYoutube.setSafeOnClickListener {
            launchActivity(YoutubeActivity::class.java)
        }
    }

}
