package vn.loitp.up.a.cv.scratchView

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuScratchViewBinding
import vn.loitp.up.a.cv.scratchView.scratchViewIv.ScratchViewImageActivity
import vn.loitp.up.a.cv.scratchView.scratchViewTv.ScratchViewTextActivity

@LogTag("ScratchViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuScratchViewActivity : BaseActivityFont() {
    private lateinit var binding: AMenuScratchViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuScratchViewBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuScratchViewActivity::class.java.simpleName
        }
        binding.btScratchViewImage.setSafeOnClickListener {
            launchActivity(ScratchViewImageActivity::class.java)
        }
        binding.btScratchViewText.setSafeOnClickListener {
            launchActivity(ScratchViewTextActivity::class.java)
        }
    }
}
