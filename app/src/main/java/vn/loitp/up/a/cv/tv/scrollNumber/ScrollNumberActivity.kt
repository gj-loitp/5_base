package vn.loitp.up.a.cv.tv.scrollNumber

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATvScrollNumberBinding

@LogTag("ScrollNumberActivity")
@IsFullScreen(false)
class ScrollNumberActivity : BaseActivityFont() {
    private lateinit var binding: ATvScrollNumberBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvScrollNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScrollNumberActivity::class.java.simpleName
        }
        binding.multiScrollNumber.setTextColors(
            intArrayOf(
                com.loitp.R.color.red50,
                com.loitp.R.color.orange,
                com.loitp.R.color.blue,
                com.loitp.R.color.green,
                com.loitp.R.color.purple
            )
        )
        // scrollNumber.setTextSize(64)

        // scrollNumber.setNumber(64, 2048)
        // scrollNumber.setInterpolator(new DecelerateInterpolator())

        binding.multiScrollNumber.setScrollVelocity(20)
        binding.multiScrollNumber.setNumber(20.48)
    }
}
