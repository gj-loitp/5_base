package com.loitpcore.views.textView.typeWriter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LTypeWriterTextView : AppCompatTextView {
    private var mText: CharSequence? = null
    private var mIndex: Int = 0
    private var mDelay: Long = 150 // in ms

    private val mHandler = Handler(Looper.getMainLooper())

    private val characterAdder: Runnable = run {
        Runnable {
            mText?.let {
                text = it.subSequence(0, mIndex++)
                if (mIndex <= it.length) {
                    mHandler.postDelayed(characterAdder, mDelay)
                }
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun animateText(txt: CharSequence) {
        mText = txt
        mIndex = 0

        text = ""
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }

    fun setCharacterDelay(m: Long) {
        mDelay = m
    }
}
