package vn.loitp.up.a.cv.fancyShowcase

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFancyShowcase
import com.loitp.core.common.NOT_FOUND
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.databinding.AFancyShowcaseSecondBinding

@LogTag("SecondActivityFancyShowcaseFont")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SecondActivityFancyShowcase : BaseActivityFancyShowcase() {
    private lateinit var binding: AFancyShowcaseSecondBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFancyShowcaseSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        focusOnButton()
        binding.button1.setOnClickListener {
            focusOnButton()
        }
    }

    private fun focusOnButton() {
        FancyShowCaseView.Builder(this@SecondActivityFancyShowcase).focusOn(binding.button1)
            .title("Focus a view").fitSystemWindows(true).build().show()
    }

}
