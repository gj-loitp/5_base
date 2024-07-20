package vn.loitp.up.a.cv.layout.swipeBack

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.transActivityNoAnimation
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import com.loitp.views.layout.swipeBack.SwipeBackLayout.OnSwipeBackListener
import vn.loitp.R
import vn.loitp.databinding.ALayoutSwipeBackBinding

// https://github.com/gongwen/SwipeBackLayout

@LogTag("SwipeBackLayoutActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class SwipeBackLayoutActivity : BaseActivityFont() {
    private var screenW = 0
    private var screenH = 0
    private lateinit var binding: ALayoutSwipeBackBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutSwipeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenW = screenWidth
        screenH = screenHeight

        setupViews()
    }

    private fun setupViews() {
        binding.swipeBackLayout.directionMode = SwipeBackLayout.FROM_LEFT
        binding.swipeBackLayout.setMaskAlpha(125)
        binding.swipeBackLayout.setSwipeBackFactor(0.5f)

        binding.swipeBackLayout.setSwipeBackListener(object : OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
                logD("onViewPositionChanged swipeBackFraction $swipeBackFraction")
                val newY = screenH * swipeBackFraction
                binding.view.translationY = newY
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                logD("onViewSwipeFinished")
                if (isEnd) {
                    finish()//correct
                    this@SwipeBackLayoutActivity.transActivityNoAnimation()
                }
            }
        })
        binding.radioGroup.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
            when (checkedId) {
                R.id.fromLeftRb -> binding.swipeBackLayout.directionMode = SwipeBackLayout.FROM_LEFT
                R.id.fromTopRb -> binding.swipeBackLayout.directionMode = SwipeBackLayout.FROM_TOP
                R.id.fromRightRb -> binding.swipeBackLayout.directionMode =
                    SwipeBackLayout.FROM_RIGHT
                R.id.fromBottomRb -> binding.swipeBackLayout.directionMode =
                    SwipeBackLayout.FROM_BOTTOM
            }
        }
    }

    override fun onDestroy() {
        logD("onDestroy")
        super.onDestroy()
    }
}
