package vn.loitp.app.activity.customviews.layout.heartLayout

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_heart_layout.*
import vn.loitp.app.R
import java.util.*

@LogTag("HeartLayoutActivity")
@IsFullScreen(false)
class HeartLayoutActivity : BaseFontActivity() {
    private val mRandom = Random()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_heart_layout
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
