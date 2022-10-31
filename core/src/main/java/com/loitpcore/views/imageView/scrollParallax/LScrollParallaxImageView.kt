package com.loitpcore.views.imageView.scrollParallax

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by gjz on 25/11/2016.
 */
class LScrollParallaxImageView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(
    context, attrs, defStyleAttr
),
    ViewTreeObserver.OnScrollChangedListener {

    interface ParallaxStyle {
        fun onAttachedToImageView(view: LScrollParallaxImageView)
        fun onDetachedFromImageView(view: LScrollParallaxImageView)
        fun transform(view: LScrollParallaxImageView, canvas: Canvas, x: Int, y: Int)
    }

    private val viewLocation = IntArray(2)
    private var enableScrollParallax = true
    private var parallaxStyles: ParallaxStyle? = null

    override fun onDraw(canvas: Canvas) {
        if (!enableScrollParallax || drawable == null) {
            super.onDraw(canvas)
            return
        }
        getLocationInWindow(viewLocation)
        parallaxStyles?.transform(
            view = this,
            canvas = canvas,
            x = viewLocation[0],
            y = viewLocation[1]
        )
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnScrollChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        viewTreeObserver.removeOnScrollChangedListener(this)
        super.onDetachedFromWindow()
    }

    override fun onScrollChanged() {
        if (enableScrollParallax) {
            invalidate()
        }
    }

    fun setParallaxStyles(styles: ParallaxStyle?) {
        parallaxStyles?.onDetachedFromImageView(this)
        parallaxStyles = styles
        parallaxStyles?.onAttachedToImageView(this)
    }

    @Suppress("unused")
    fun setEnableScrollParallax(enableScrollParallax: Boolean) {
        this.enableScrollParallax = enableScrollParallax
    }
}
