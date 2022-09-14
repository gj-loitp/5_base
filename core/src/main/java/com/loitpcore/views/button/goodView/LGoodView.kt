package com.loitpcore.views.button.goodView

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LGoodView(
    context: Context?
) : PopupWindow(context), IGoodView {

    companion object {
        private fun getTextViewHeight(textView: TextView?, width: Int): Int {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST)
            val heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            textView?.measure(widthMeasureSpec, heightMeasureSpec)
            return textView?.measuredHeight ?: 0
        }
    }

    private var mText: String? = IGoodView.TEXT
    private var mTextColor = IGoodView.TEXT_COLOR
    private var mTextSize = IGoodView.TEXT_SIZE
    private var mFromY = IGoodView.FROM_Y_DELTA
    private var mToY = IGoodView.TO_Y_DELTA
    private var mFromAlpha = IGoodView.FROM_ALPHA
    private var mToAlpha = IGoodView.TO_ALPHA
    private var mDuration = IGoodView.DURATION
    private var mDistance = IGoodView.DISTANCE
    private var mAnimationSet: AnimationSet? = null
    private var mChanged = false
    private var mContext: Context? = null
    private var mGood: TextView? = null

    init {
        mContext = context
        initView()
    }

    private fun initView() {
        val layout = RelativeLayout(mContext)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        mGood = TextView(mContext)
        mGood?.apply {
            this.includeFontPadding = false
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize.toFloat())
            this.setTextColor(mTextColor)
            this.text = mText
            this.layoutParams = params
            layout.addView(this)
        }

        contentView = layout
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        mGood?.let {
            mGood?.measure(w, h)
            width = it.measuredWidth
            height = mDistance + it.measuredHeight
        }
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = false
        isTouchable = false
        isOutsideTouchable = false
        mAnimationSet = createAnimation()
    }

    fun setText(text: String?) {
        require(!TextUtils.isEmpty(text)) {
            "text cannot be null."
        }
        mText = text
        mGood?.let {
            it.text = text
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val w = it.paint.measureText(text).toInt()
            width = w
            height = mDistance + getTextViewHeight(textView = it, width = w)
        }
    }

    private fun setTextColor(color: Int) {
        mTextColor = color
        mGood?.setTextColor(color)
    }

    private fun setTextSize(textSize: Int) {
        mTextSize = textSize
        mGood?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
    }

    fun setTextInfo(text: String?, textColor: Int, textSize: Int) {
        setTextColor(textColor)
        setTextSize(textSize)
        setText(text)
    }

    fun setImage(resId: Int) {
        setImage(mContext?.resources?.getDrawable(resId))
    }

    fun setImage(drawable: Drawable?) {
        requireNotNull(drawable) {
            "drawable cannot be null."
        }
        mGood?.background = drawable
        mGood?.text = ""
        width = drawable.intrinsicWidth
        height = mDistance + drawable.intrinsicHeight
    }

    fun setDistance(dis: Int) {
        mDistance = dis
        mToY = dis
        mChanged = true
        mGood?.let {
            height = mDistance + it.measuredHeight
        }
    }

    fun setTranslateY(fromY: Int, toY: Int) {
        mFromY = fromY
        mToY = toY
        mChanged = true
    }

    fun setAlpha(fromAlpha: Float, toAlpha: Float) {
        mFromAlpha = fromAlpha
        mToAlpha = toAlpha
        mChanged = true
    }

    fun setDuration(duration: Int) {
        mDuration = duration
        mChanged = true
    }

    fun reset() {
        mText = IGoodView.TEXT
        mTextColor = IGoodView.TEXT_COLOR
        mTextSize = IGoodView.TEXT_SIZE
        mFromY = IGoodView.FROM_Y_DELTA
        mToY = IGoodView.TO_Y_DELTA
        mFromAlpha = IGoodView.FROM_ALPHA
        mToAlpha = IGoodView.TO_ALPHA
        mDuration = IGoodView.DURATION
        mDistance = IGoodView.DISTANCE
        mChanged = false
        mAnimationSet = createAnimation()
    }

    fun show(v: View) {
        if (!isShowing) {
            val offsetY = -v.height - height
            showAsDropDown(v, v.width / 2 - width / 2, offsetY)
            if (mAnimationSet == null || mChanged) {
                mAnimationSet = createAnimation()
                mChanged = false
            }
            mGood?.startAnimation(mAnimationSet)
        }
    }

    private fun createAnimation(): AnimationSet? {
        mAnimationSet = AnimationSet(true)
        val translateAnim = TranslateAnimation(0f, 0f, mFromY.toFloat(), (-mToY).toFloat())
        val alphaAnim = AlphaAnimation(mFromAlpha, mToAlpha)
        mAnimationSet?.apply {
            this.addAnimation(translateAnim)
            this.addAnimation(alphaAnim)
            this.duration = mDuration.toLong()
            this.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (isShowing) {
                        Handler(Looper.getMainLooper()).post { dismiss() }
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }

        return mAnimationSet
    }
}
