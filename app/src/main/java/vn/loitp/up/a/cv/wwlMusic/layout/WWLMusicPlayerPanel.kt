package vn.loitp.up.a.cv.wwlMusic.layout

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import vn.loitp.R

class WWLMusicPlayerPanel(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    private var mBgColorDrawable: ColorDrawable = ColorDrawable()
    private var layoutMiniControlsContainer: View? = null
    private var layoutMusicPlayerView: FrameLayout? = null
    private var playButton: ImageView? = null
    private var pauseButton: ImageView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()

        layoutMusicPlayerView = findViewById(R.id.layoutMusicPlayerView)
        layoutMiniControlsContainer = findViewById(R.id.layoutMiniControlsContainer)
        playButton = layoutMiniControlsContainer?.findViewById(R.id.playButton)
        pauseButton = layoutMiniControlsContainer?.findViewById(R.id.pauseButton)
        layoutMiniControlsContainer?.background = mBgColorDrawable
        layout()
        isFocusable = true
        isFocusableInTouchMode = true
    }

    fun layout() {
        mBgColorDrawable.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        val layoutParams = layoutMusicPlayerView?.layoutParams as LayoutParams?
        layoutParams?.gravity = Gravity.START
        layoutMusicPlayerView?.layoutParams = layoutParams
        requestLayout()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val w169 = ((height + 2).toFloat() * 1.777f).toInt()
        layoutMusicPlayerView?.measure(
            MeasureSpec.makeMeasureSpec(w169, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
        )
        if (layoutMiniControlsContainer?.visibility == VISIBLE) {
            layoutMiniControlsContainer?.measure(
                MeasureSpec.makeMeasureSpec(
                    width - w169,
                    MeasureSpec.EXACTLY
                ),
                heightMeasureSpec
            )
        }
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)

        layoutMusicPlayerView?.let { fl ->
            val innerH = bottom - top
            val playerW = fl.measuredWidth
            val playerH = fl.measuredHeight
            fl.layout(0, 0, playerW, playerH)

            layoutMiniControlsContainer?.let {
                if (it.visibility == VISIBLE) {
                    it.layout(playerW, 0, it.measuredWidth + playerW, innerH)
                }
            }
        }
    }
}
