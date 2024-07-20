package vn.loitp.up.a.anim.overScroll

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.readTxtFromRawFolder
import com.loitp.core.ext.setPullLikeIOSVertical
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationOverScrollBinding

@LogTag("OverScrollActivity")
@IsFullScreen(false)
class OverScrollActivity : BaseActivityFont() {

    private lateinit var binding: AAnimationOverScrollBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationOverScrollBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = OverScrollActivity::class.java.simpleName
        }

        binding.nsv.setPullLikeIOSVertical()
        binding.textView.text = this.readTxtFromRawFolder(nameOfRawFile = R.raw.overscroll)
    }
}
