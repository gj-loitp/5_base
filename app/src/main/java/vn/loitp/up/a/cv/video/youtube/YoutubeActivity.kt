package vn.loitp.up.a.cv.video.youtube

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.playYoutube
import com.loitp.core.ext.playYoutubeWithId
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVideoYoutubeBinding

@LogTag("YoutubeActivity")
@IsFullScreen(false)
class YoutubeActivity : BaseActivityFont() {
    private lateinit var binding: AVideoYoutubeBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVideoYoutubeBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = YoutubeActivity::class.java.simpleName
        }
        binding.bt0.setSafeOnClickListener {
            this.playYoutube(
                url = "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender"
            )
        }
        binding.bt1.setSafeOnClickListener {
            this.playYoutubeWithId(id = "YE7VzlLtp-4")
        }
    }
}
