package com.views.edittext.leditext

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.core.utilities.LUIUtil
import com.google.android.material.card.MaterialCardView
import com.utils.util.ConvertUtils
import com.views.OnSingleClickListener

class LEditText : RelativeLayout {
    private val TAG = javaClass.simpleName
    lateinit var mcv: MaterialCardView
    lateinit var editText: EditText
    lateinit var ivLeft: ImageView
    lateinit var ivRight: ImageView
    lateinit var tvMessage: TextView
    lateinit var ll: LinearLayout
    lateinit var rootView: ConstraintLayout

    var callback: Callback? = null
    var colorFocus = Color.BLACK
    var colorUnfocus = Color.GRAY
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

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, loitp.core.R.layout.view_l_edittext, this)
        mcv = findViewById(loitp.core.R.id.mcv)
        editText = findViewById(loitp.core.R.id.editText)
        ivLeft = findViewById(loitp.core.R.id.ivLeft)
        ivRight = findViewById(loitp.core.R.id.ivRight)
        tvMessage = findViewById(loitp.core.R.id.tvMessage)
        ll = findViewById(loitp.core.R.id.ll)
        rootView = findViewById(loitp.core.R.id.rootView)

        editText.setOnFocusChangeListener { view, isFocus ->
            if (isFocus) {
                mcv.strokeColor = colorFocus
            } else {
                mcv.strokeColor = colorUnfocus
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
                    mcv.strokeColor = colorUnfocus
                }
                hideMessage()
                callback?.onTextChanged(p0.toString())
            }
        })

        ivRight.setOnClickListener {
            object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    callback?.onClickIvRight(ivRight)
                }
            }
        }
    }

    fun setStrokeWidth(width: Int) {
        mcv.strokeWidth = width
    }

    fun setCardElevation(elevation: Float) {
        mcv.cardElevation = elevation
        mcv.useCompatPadding = true
        //LUIUtil.setMarginsDp(mcv, elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt())
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
        if (maxLines == 1) {
            editText.setSingleLine(true)
        } else {
            editText.setSingleLine(false)
        }
    }

    fun setWidthRootView(width: Int) {
        rootView.layoutParams.width = width
        rootView.requestLayout()
    }

    fun setHeightRootView(height: Int) {
        rootView.layoutParams.height = height
        rootView.requestLayout()
    }

    fun disableEditing() {
        editText.isFocusable = false
    }

    fun enableEditing() {
        editText.isFocusableInTouchMode = true
    }

    interface Callback {
        fun setOnFocusChangeListener(isFocus: Boolean)
        fun onTextChanged(s: String)
        fun onClickIvRight(imageView: ImageView)
    }
}