package com.views.edittext.leditext

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.core.utilities.LUIUtil
import com.google.android.material.card.MaterialCardView
import loitp.core.R
import kotlin.math.roundToInt

class LEditText : RelativeLayout {
    private val TAG = javaClass.simpleName
    lateinit var mcv: MaterialCardView
    lateinit var editText: EditText
    lateinit var ivLeft: ImageView
    lateinit var ivRight: ImageView
    lateinit var tvMessage: TextView

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

        editText.setOnFocusChangeListener { view, isFocus ->
            if (isFocus) {
                mcv.strokeColor = colorFocus
            } else {
                mcv.strokeColor = colorUnfocus
            }
            callback?.setOnFocusChangeListener(isFocus)
        }
    }

    fun setStrokeWidth(width: Int) {
        mcv.strokeWidth = width
    }

    fun setCardElevation(elevation: Float) {
        mcv.cardElevation = elevation
        LUIUtil.setMarginsDp(mcv, elevation.roundToInt(), elevation.roundToInt(), elevation.roundToInt(), 0)
        LUIUtil.setMarginsDp(tvMessage, elevation.roundToInt(), 0, elevation.roundToInt(), 0)
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

    fun showMessage(text: String) {
        tvMessage.text = text
        tvMessage.visibility = View.VISIBLE
    }

    fun hideMessage() {
        tvMessage.visibility = View.GONE
    }

    public interface Callback {
        fun setOnFocusChangeListener(isFocus: Boolean)
    }
}