package com.loitpcore.views.textView.selectable

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.loitpcore.R
import com.loitp.core.utilities.LAppResource
import com.loitpcore.views.textView.selectable.CommonUtil.dpTpPx
import com.loitpcore.views.textView.selectable.CustomTextView.OnCursorStateChangedListener

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LSelectableView : FrameLayout {
    private var scrollView: ObservableScrollView? = null
    private var saveBtn: TextView? = null
    private var selectableListener: SelectableListener? = null
    private var hasActionBar = false

    fun setText(text: String?) {
        scrollView?.setText(text)
    }

    fun selectAll() {
        saveBtn?.visibility = VISIBLE
        scrollView?.selectAll()
    }

    fun hideCursor() {
        saveBtn?.visibility = GONE
        scrollView?.hideCursor()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        scrollView = ObservableScrollView(context)
        this.addView(
            scrollView,
            LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        )
        initSaveButton()
        addOnCursorStateChangedListener()
    }

    private fun initSaveButton() {
        saveBtn = TextView(context)
        saveBtn?.apply {
            this.gravity = Gravity.CENTER
            this.text = context.resources.getString(R.string.save_conversation)
            this.setTextColor(LAppResource.getColor(R.color.deepPink))
            this.setBackgroundResource(R.drawable.selector_highlight_btn_bg)
            this.visibility = GONE
            this.setOnClickListener {
                val customInfo = scrollView?.customTextView?.cursorSelection
                selectableListener?.selectedText(customInfo?.selectedText)
            }
        }

        this.addView(saveBtn)
    }

    @Suppress("NAME_SHADOWING")
    private fun setHighlightBtnCoods(x: Int, y: Int) {
        var x = x
        var y = y
        scrollView?.customTextView?.measure(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        y = if (hasActionBar) {
            y - dpTpPx(37f, context)
        } else {
            y + dpTpPx(20f, context)
        }
        val deviceWidth = resources.displayMetrics.widthPixels
        if (x > deviceWidth - dpTpPx(75f, context)) {
            x = deviceWidth - dpTpPx(95f, context)
        }
        val params = LayoutParams(dpTpPx(75f, context), dpTpPx(35f, context))
        params.leftMargin = x
        params.topMargin = y
        saveBtn?.layoutParams = params
    }

    private fun addOnCursorStateChangedListener() {
        scrollView?.customTextView?.setOnCursorStateChangedListener(
            object : OnCursorStateChangedListener {
                override fun onDragStarts(v: View?) {
                    saveBtn?.visibility = GONE
                }

                override fun onPositionChanged(v: View?, x: Int, y: Int, oldx: Int, oldy: Int) {
                }

                override fun onDragEnds(endHandleX: Int, endHandleY: Int) {
                    if (isHighlightButtonVisible) {
                        saveBtn?.visibility = VISIBLE
                    }
                    setHighlightBtnCoods(x = endHandleX, y = endHandleY)
                }
            }
        )
    }

    private val isHighlightButtonVisible: Boolean
        get() = scrollView?.customTextView?.cursorSelection?.end != scrollView?.customTextView?.cursorSelection?.start

    fun addOnSaveClickListener(selectableListener: SelectableListener?) {
        this.selectableListener = selectableListener
    }

    fun setActivity(act: AppCompatActivity) {
        hasActionBar = act.supportActionBar != null
    }

    fun setActivity(act: Activity) {
        hasActionBar = act.actionBar != null
    }
}
