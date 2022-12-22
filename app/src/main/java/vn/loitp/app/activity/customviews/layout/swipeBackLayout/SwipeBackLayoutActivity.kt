package vn.loitp.app.activity.customviews.layout.swipeBackLayout

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LScreenUtil
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import com.loitp.views.layout.swipeBack.SwipeBackLayout.OnSwipeBackListener
import kotlinx.android.synthetic.main.activity_swipe_back_layout.*
import vn.loitp.R

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
                    finish()//correct
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
