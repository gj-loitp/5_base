package vn.loitp.up.a.cv.lCv

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.URL_IMG_2
import com.loitp.core.common.URL_IMG_4
import com.loitp.core.ext.play
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.card.LCardView
import vn.loitp.databinding.ALCardViewBinding

@LogTag("LCardViewActivity")
@IsFullScreen(false)
class LCardViewActivity : BaseActivityFont() {

    private lateinit var binding: ALCardViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALCardViewBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = LCardViewActivity::class.java.simpleName
        }
        binding.lCardView0.apply {
            callback = object : LCardView.Callback {
                override fun onClickRoot(v: View) {
                    // LAnimationUtil.play(v, Techniques.Pulse)
                }

                override fun onLongClickRoot(v: View) {
                    // LAnimationUtil.play(v, Techniques.Pulse)
                }

                override fun onClickText(v: View) {
                    v.play(techniques = Techniques.Pulse)
                }

                override fun onLongClickText(v: View) {
                    v.play(techniques = Techniques.Pulse)
                }
            }
            setText(System.currentTimeMillis().toString() + "")
            height = 300
            width = screenWidth * 3 / 4
            setRadius(30f)
            setCardElevation(10f)
            setImg(URL_IMG_2)
        }
        binding.lCardView1.apply {
            setText(System.currentTimeMillis().toString() + "")
            height = screenHeight * 3 / 2
            setRadius(50f)
            setCardElevation(20f)
            setImg(URL_IMG_4)
        }
    }
}
