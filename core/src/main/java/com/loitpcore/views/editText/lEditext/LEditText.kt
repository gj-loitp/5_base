package com.loitpcore.views.editText.lEditext

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import com.loitpcore.R
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.utils.util.ConvertUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LEditText : RelativeLayout {
    lateinit var mcv: MaterialCardView
    lateinit var editText: EditText
    lateinit var ivLeft: ImageView
    lateinit var ivRight: ImageView
    @Suppress("unused")
    lateinit var tvMessage: TextView
    lateinit var ll: LinearLayout
    lateinit var rootView: ConstraintLayout

    var callback: Callback? = null
    var colorFocus = Color.BLACK
    var colorUnFocus = Color.GRAY
        set(value) {
            if (editText.isFocused) {
                mcv.strokeColor = colorFocus
            } else {
                mcv.strokeColor = value
            }
            field = value
        }
    var colorError = Color.RED
        set(value) {
            tvMessage.setTextColor(value)
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_edittext, this)
        mcv = findViewById(R.id.mcv)
        editText = findViewById(R.id.editText)
        ivLeft = findViewById(R.id.ivLeft)
        ivRight = findViewById(R.id.ivRight)
        tvMessage = findViewById(R.id.tvMessage)
        ll = findViewById(R.id.ll)
        rootView = findViewById(R.id.rootView)

        editText.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                mcv.strokeColor = colorFocus
            } else {
                mcv.strokeColor = colorUnFocus
            }
            callback?.setOnFocusChangeListener(isFocus)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editText.isFocused) {
                    mcv.strokeColor = colorFocus
                } else {
                    mcv.strokeColor = colorUnFocus
                }
                hideMessage()
                callback?.onTextChanged(p0.toString())
            }
        })

        ivRight.setOnClickListener { callback?.onClickIvRight(ivRight) }
    }

    fun setStrokeWidth(width: Int) {
        mcv.strokeWidth = width
    }

    fun setCardElevation(elevation: Float) {
        mcv.cardElevation = elevation
        mcv.useCompatPadding = true
        // LUIUtil.setMarginsDp(mcv, elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt())
    }

    fun setPaddingDp(paddingDp: Float) {
        val paddingPx = ConvertUtils.dp2px(paddingDp)
        ll.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
    }

    fun setCardBackgroundColor(color: Int) {
        mcv.setCardBackgroundColor(color)
    }

    fun setCardRadius(radius: Float) {
        mcv.radius = radius
    }

    fun setLastCursorEditText() {
        LUIUtil.setLastCursorEditText(editText)
    }

    fun setText(s: String) {
        editText.setText(s)
    }

    fun showMessage(text: String) {
        tvMessage.text = text
        tvMessage.visibility = View.VISIBLE
        mcv.strokeColor = colorError
    }

    fun hideMessage() {
        tvMessage.visibility = View.INVISIBLE
    }

    fun setInputType(inputType: Int) {
        editText.inputType = inputType
    }

    fun setImeiActionEditText(imeOptions: Int, runnable: Runnable?) {
        LUIUtil.setImeiActionEditText(editText, imeOptions, runnable)
    }

    fun setMaxLines(maxLines: Int) {
        editText.maxLines = maxLines
        editText.isSingleLine = maxLines == 1
    }

    fun setWidthRootView(width: Int) {
        rootView.layoutParams.width = width
        rootView.requestLayout()
    }

    fun setHeightRootView(height: Int) {
        rootView.layoutParams.height = height
        rootView.requestLayout()
    }

    interface Callback {
        fun setOnFocusChangeListener(isFocus: Boolean)
        fun onTextChanged(s: String)
        fun onClickIvRight(imageView: ImageView)
    }
}
