package vn.loitp.a.cv.layout.heart

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_heart.*
import vn.loitp.R
import java.util.*

@LogTag("HeartLayoutActivity")
@IsFullScreen(false)
class HeartLayoutActivity : BaseFontActivity() {
    private val mRandom = Random()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_heart
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = HeartLayoutActivity::class.java.simpleName
        }
        rootView.setOnClickListener {
            heartLayout.addHeart(randomColor())
        }
    }

    private fun randomColor(): Int {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
    }
}
