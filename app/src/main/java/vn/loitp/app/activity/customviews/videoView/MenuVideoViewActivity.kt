package vn.loitp.app.activity.customviews.videoView

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_video.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.videoView.exo.ExoPlayerActivity
import vn.loitp.app.activity.customviews.videoView.exo.ExoPlayerActivity2
import vn.loitp.app.activity.customviews.videoView.exo.ExoPlayerActivity3
import vn.loitp.app.activity.customviews.videoView.youtube.YoutubeActivity

@LogTag("VideoViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuVideoViewActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_video
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuVideoViewActivity::class.java.simpleName
        }

        btExoPlayer2.setOnClickListener(this)
        btExoPlayer2IMA.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        btYoutube.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btExoPlayer2 -> {
                intent = Intent(this, ExoPlayerActivity::class.java)
                intent.putExtra(
                    Constants.KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
            }
            btExoPlayer2IMA -> {
                intent = Intent(this, ExoPlayerActivity::class.java)
                intent.putExtra(
                    Constants.KEY_VIDEO_LINK_PLAY,
                    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
                )
                intent.putExtra(Constants.KEY_VIDEO_LINK_IMA_AD, getString(R.string.ad_tag_url))
            }
            bt2 -> intent = Intent(this, ExoPlayerActivity2::class.java)
            bt3 -> intent = Intent(this, ExoPlayerActivity3::class.java)
            btYoutube -> intent = Intent(this, YoutubeActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
