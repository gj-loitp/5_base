package vn.loitp.up.a.anim.basicTransition

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationBasicTransition1Binding
import vn.loitp.up.common.Constants.Companion.URL_IMG_2

@LogTag("BasicTransition1Activity")
@IsFullScreen(false)
class BasicTransition1Activity : BaseActivityFont() {

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }

    private lateinit var binding: AAnimationBasicTransition1Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationBasicTransition1Binding.inflate(layoutInflater)
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
            this.tvTitle?.text = BasicTransition1Activity::class.java.simpleName
        }
        binding.imageViewItem.loadGlide(
            any = URL_IMG_2,
        )
        ViewCompat.setTransitionName(binding.imageViewItem, IV)
        ViewCompat.setTransitionName(binding.textView, TV)
    }
}
