package vn.loitp.up.a.cv.layout.scrollView2d

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ALayoutScrollView2dBinding

@LogTag("ScrollView2DActivity")
@IsFullScreen(false)
class ScrollView2DActivity : BaseActivityFont() {
    private lateinit var binding: ALayoutScrollView2dBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutScrollView2dBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ScrollView2DActivity::class.java.simpleName
        }
        binding.twoDScrollView.setScrollChangeListener { _, x, y, oldX, oldY ->
            logD("setScrollChangeListener $x - $y - $oldX - $oldY")
        }
        setDelay(
            mls = 2000,
            runnable = {
                binding.twoDScrollView.smoothScrollTo(300, 300)
            }
        )
    }
}
