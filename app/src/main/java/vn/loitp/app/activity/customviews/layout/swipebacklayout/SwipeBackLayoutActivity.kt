package vn.loitp.app.activity.customviews.layout.swipebacklayout

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LLog
import com.core.utilities.LScreenUtil
import com.views.layout.swipeback.SwipeBackLayout
import com.views.layout.swipeback.SwipeBackLayout.OnSwipeBackListener
import kotlinx.android.synthetic.main.activity_swipeback_layout.*
import loitp.basemaster.R

//https://github.com/gongwen/SwipeBackLayout
class SwipeBackLayoutActivity : BaseFontActivity() {
    private var screenW = 0
    private var screenH = 0

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipeback_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenW = LScreenUtil.screenWidth
        screenH = LScreenUtil.screenHeight

        swipeBackLayout.directionMode = SwipeBackLayout.FROM_LEFT
        swipeBackLayout.maskAlpha = 125
        swipeBackLayout.swipeBackFactor = 0.5f

        swipeBackLayout.setSwipeBackListener(object : OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
                LLog.d(TAG, "onViewPositionChanged swipeBackFraction $swipeBackFraction")
                val newY = screenH * swipeBackFraction
                view.translationY = newY
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                LLog.d(TAG, "onViewSwipeFinished")
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAniamtion(activity)
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
        LLog.d(TAG, "onDestroy")
        super.onDestroy()
    }
}