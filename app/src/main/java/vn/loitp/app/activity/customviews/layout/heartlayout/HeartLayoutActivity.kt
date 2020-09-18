package vn.loitp.app.activity.customviews.layout.heartlayout

import android.graphics.Color
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.heartlayout.LHeartLayout
import kotlinx.android.synthetic.main.activity_heart_layout.*
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_heart_layout)
@LogTag("HeartLayoutActivity")
class HeartLayoutActivity : BaseFontActivity() {
    private val mRandom = Random()
    private lateinit var mLHeartLayout: LHeartLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLHeartLayout = findViewById(R.id.heart_layout)
        rootView.setOnClickListener { _ -> mLHeartLayout.addHeart(randomColor()) }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    private fun randomColor(): Int {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
    }
}
