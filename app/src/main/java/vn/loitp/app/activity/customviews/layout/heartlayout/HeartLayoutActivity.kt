package vn.loitp.app.activity.customviews.layout.heartlayout

import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_heart_layout.*
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_heart_layout)
@LogTag("HeartLayoutActivity")
@IsFullScreen(false)
class HeartLayoutActivity : BaseFontActivity() {
    private val mRandom = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootView.setOnClickListener {
            heartLayout.addHeart(randomColor())
        }
    }

    private fun randomColor(): Int {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
    }
}
