package com.loitpcore.views.et.animatedExpandable

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatEditText
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LAnimatedExpandableEditText : AppCompatEditText {

    companion object {
        var expandHeightPixels = 0
        var expandAnimationDurationMilliseconds = 0
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        expandAnimationDurationMilliseconds =
            resources.getInteger(R.integer.animated_expanded_edit_text_animation_duration_milliseconds)
        expandHeightPixels =
            resources.getDimensionPixelSize(R.dimen.animated_expandable_edit_text_expanded_height)
        onFocusChangeListener = getOnFocusChangeListener(this)
        setOnEditorActionListener(getOnEditorActionListener(this))
    }

    private fun getOnEditorActionListener(editText: EditText): OnEditorActionListener {
        return OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.clearFocus()
                if (editText.context != null) {
                    closeKeyboard(v, editText)
                }
                return@OnEditorActionListener true
            }
            false
        }
    }

    private fun closeKeyboard(
        tv: TextView,
        editText: EditText
    ) {
        val imm =
            editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv.windowToken, 0)
    }

    private fun getOnFocusChangeListener(editText: EditText): OnFocusChangeListener {
        return OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                expandViewWithAnimation(editText = editText, pixelsToExpand = expandHeightPixels)
            } else {
                compactViewWithAnimation(editText = editText, pixelsToExpand = expandHeightPixels)
            }
        }
    }

    private fun expandViewWithAnimation(
        editText: EditText,
        pixelsToExpand: Int
    ) {
        val animation = ValueAnimator.ofInt(editText.height, editText.height + pixelsToExpand)
        animateEditTextSize(editText, animation)
    }

    private fun compactViewWithAnimation(
        editText: EditText,
        pixelsToExpand: Int
    ) {
        val animation = ValueAnimator.ofInt(editText.height, editText.height - pixelsToExpand)
        animateEditTextSize(editText, animation)
    }

    private fun animateEditTextSize(
        editText: EditText,
        animation: ValueAnimator
    ) {
        animation.duration = expandAnimationDurationMilliseconds.toLong()
        animation.addUpdateListener { a ->
            val value = a.animatedValue as Int
            editText.layoutParams.height = value
            editText.requestLayout()
        }
        animation.start()
    }
}
