package com.views.edittext.leditext

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.core.utilities.LUIUtil
import com.google.android.material.card.MaterialCardView
import com.utils.util.ConvertUtils
import com.views.OnSingleClickListener
import loitp.core.R
import kotlin.math.roundToInt


class LEditText : RelativeLayout {
    private val TAG = javaClass.simpleName
    lateinit var mcv: MaterialCardView
    lateinit var editText: EditText
    lateinit var ivLeft: ImageView
    lateinit var ivRight: ImageView
    lateinit var tvMessage: TextView
    lateinit var ll: LinearLayout

    var callback: Callback? = null
    var colorFocus = Color.RED
    var colorUnfocus = Color.GRAY

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
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
                callback?.onTextChanged(p0.toString())
            }
        })

        ivRight.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                callback?.onClickIvRight(ivRight)
            }
        })
    }

    fun setStrokeWidth(width: Int) {
        mcv.strokeWidth = width
    }

    fun setCardElevation(elevation: Float) {
        mcv.cardElevation = elevation
        LUIUtil.setMarginsDp(mcv, elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt())
        LUIUtil.setMargins(tvMessage, elevation.roundToInt(), 0, elevation.roundToInt(), 0)
        /*if (elevation == 0f) {
            val params = tvMessage.layoutParams as LayoutParams
            params.addRule(BELOW, R.id.mcv);
            tvMessage.layoutParams = params
        } else {
            val mcvHeight = LUIUtil.getHeightOfView(mcv)
            LLog.d(TAG, "setCardElevation marginTop: $mcvHeight")
            LUIUtil.setMargins(tvMessage, elevation.roundToInt(), mcvHeight + elevation.roundToInt(), elevation.roundToInt(), 0)
        }*/
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

    interface Callback {
        fun setOnFocusChangeListener(isFocus: Boolean)
        fun onTextChanged(s: String)
        fun onClickIvRight(imageView: ImageView)
    }
}