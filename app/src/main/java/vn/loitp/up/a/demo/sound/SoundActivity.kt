package vn.loitp.up.a.demo.sound

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.startMusicFromAsset
import vn.loitp.R
import vn.loitp.databinding.ADemoSoundBinding

@LogTag("SoundActivity")
@IsFullScreen(false)
class SoundActivity : BaseActivityFont(), OnClickListener {

    private lateinit var binding: ADemoSoundBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoSoundBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = SoundActivity::class.java.simpleName
        }
        binding.btPlay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btPlay -> this.startMusicFromAsset(fileName = "ting.ogg")
        }
    }
}
