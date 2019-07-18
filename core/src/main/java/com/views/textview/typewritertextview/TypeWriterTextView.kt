package com.views.textview.typewritertextview

import android.content.Context
import android.os.Handler
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

class TypeWriterTextView : AppCompatTextView {
    private var mText: CharSequence? = null
    private var mIndex: Int = 0
    private var mDelay: Long = 150 // in ms

    private val mHandler = Handler()

    val characterAdder: Runnable = run {
        Runnable {
            mText?.let {
                text = it.subSequence(0, mIndex++)
                if (mIndex <= it.length) {
                    mHandler.postDelayed(characterAdder, mDelay)
                }
            }
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

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