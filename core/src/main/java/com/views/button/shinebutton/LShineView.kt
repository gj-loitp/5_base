package com.views.button.shinebutton

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import loitp.core.R

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

//guide: https://github.com/ChadCSong/ShineButton
class LShineView : RelativeLayout {
    private val TAG = javaClass.simpleName
    private var iv: ImageView? = null
    private var shineButton: ShineButton? = null

    private var isDelay = true

    private var callback: Callback? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_shine, this)

        this.iv = findViewById(R.id.iv)
        this.shineButton = findViewById(R.id.shine_button)
        iv?.setOnClickListener { v ->
            shineButton?.performClick()
            LAnimationUtil.play(iv, Techniques.Pulse)
            if (callback != null) {
                if (isDelay) {
                    LUIUtil.setDelay(750) { mls -> callback?.onClick(v) }
                } else {
                    callback?.onClick(v)
                }
            }
        }
    }

    fun setSize(sizeImageViewInDP: Int, sizeShineButtonInDP: Int) {
        val dimensionInDpImageView = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeImageViewInDP.toFloat(), resources.displayMetrics).toInt()
        iv?.let {
            it.layoutParams.height = dimensionInDpImageView
            it.layoutParams.width = dimensionInDpImageView
            it.requestLayout()
        }

        val dimensionInDpShineButton = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeShineButtonInDP.toFloat(), resources.displayMetrics).toInt()
        shineButton?.let {
            it.layoutParams?.height = dimensionInDpShineButton
            it.layoutParams?.width = dimensionInDpShineButton
            it.requestLayout()
        }
    }

    interface Callback {
        fun onClick(view: View)
    }

    fun setDelay(isDelay: Boolean) {
        this.isDelay = isDelay
    }

    fun setOnClick(callback: Callback) {
        this.callback = callback
    }

    fun setImage(resID: Int) {
        iv?.setImageResource(resID)
    }

    fun setEnabledIv(isEnable: Boolean) {
        iv?.isEnabled = isEnable
    }

    fun setPerformClick() {
        iv?.performClick()
    }
}