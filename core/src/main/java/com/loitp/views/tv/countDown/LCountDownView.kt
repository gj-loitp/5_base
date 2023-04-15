package com.loitp.views.tv.countDown

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import com.daimajia.androidanimations.library.Techniques
import com.loitp.R
import com.loitp.core.ext.play

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LCountDownView : RelativeLayout {

    interface Callback {
        fun onTick()
        fun onEnd()
    }

    private var number = 0
    private var callback: Callback? = null

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private lateinit var tvCountDown: AppCompatTextView
    private fun init() {
        inflate(context, R.layout.l_v_count_down, this)

        tvCountDown = findViewById(R.id.tvCountDown)
    }

    fun start(number: Int) {
        this.number = number
        doPerSec()
    }

    fun setShowOrHide(isShow: Boolean) {
        tvCountDown.visibility = if (isShow) {
            VISIBLE
        } else {
            INVISIBLE
        }
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    @SuppressLint("SetTextI18n")
    private fun doPerSec() {
        tvCountDown.text = number.toString()
        tvCountDown.play(
            techniques = Techniques.FlipInX,
            duration = 1000,
            onEnd = {
                number--
                if (number <= 0) {
                    tvCountDown.text = "GO"
                    tvCountDown.play(
                        techniques = Techniques.Flash,
                        onEnd = {
                            tvCountDown.visibility = GONE
                            callback?.onEnd()
                        }
                    )
                } else {
                    doPerSec()
                }
            }
        )
    }
}
