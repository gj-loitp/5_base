package vn.loitp.app.activity.customviews.layout.swipeBackLayout

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.views.layout.swipeback.SwipeBackLayout
import com.loitpcore.views.layout.swipeback.SwipeBackLayout.OnSwipeBackListener
import kotlinx.android.synthetic.main.activity_swipe_back_layout.*
import vn.loitp.app.R

// https://github.com/gongwen/SwipeBackLayout

@LogTag("SwipeBackLayoutActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class SwipeBackLayoutActivity : BaseFontActivity() {
    private var screenW = 0
    private var screenH = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_back_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenW = LScreenUtil.screenWidth
        screenH = LScreenUtil.screenHeight

        setupViews()
    }

    private fun setupViews() {
        swipeBackLayout.directionMode = SwipeBackLayout.FROM_LEFT
        swipeBackLayout.setMaskAlpha(125)
        swipeBackLayout.setSwipeBackFactor(0.5f)

        swipeBackLayout.setSwipeBackListener(object : OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
                logD("onViewPositionChanged swipeBackFraction $swipeBackFraction")
                val newY = screenH * swipeBackFraction
                view.translationY = newY
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                logD("onViewSwipeFinished")
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@SwipeBackLayoutActivity)
                }
            }
        })
        radioGroup.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
            when (checkedId) {
                R.id.fromLeftRb -> swipeBackLayout.directionMode = SwipeBackLayout.FROM_LEFT
                R.id.fromTopRb -> swipeBackLayout.directionMode = SwipeBackLayout.FROM_TOP
                R.id.fromRightRb -> swipeBackLayout.directionMode = SwipeBackLayout.FROM_RIGHT
                R.id.fromBottomRb -> swipeBackLayout.directionMode = SwipeBackLayout.FROM_BOTTOM
            }
        }
    }

    override fun onDestroy() {
        logD("onDestroy")
        super.onDestroy()
    }
}
