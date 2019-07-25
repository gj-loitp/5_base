package com.views.hottagkeywords

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import loitp.core.R

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LLabelView : LinearLayout {
    private var mTextView: TextView? = null
    private var listenerOnCrossClick: OnClickCrossListener? = null
    private var listenerOnLabelClick: OnLabelClickListener? = null

    var text: String
        get() = mTextView?.text.toString()
        set(text) {
            mTextView?.text = text
        }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, textSize: Int, iconCross: Int,
                showCross: Boolean, textColor: Int, backgroundResource: Int, labelsClickables: Boolean, padding: Int) : super(context) {
        init(context, textSize, iconCross, showCross, textColor,
                backgroundResource, labelsClickables, padding)
    }

    private fun init(context: Context, textSize: Int, iconCross: Int,
                     showCross: Boolean, textColor: Int, backgroundResource: Int, labelsClickables: Boolean, padding: Int) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val labelView = inflater.inflate(R.layout.view_label, this, true)

        val linearLayout = labelView.findViewById<LinearLayout>(R.id.llLabel)
        linearLayout.setBackgroundResource(backgroundResource)
        linearLayout.setPadding(padding, padding, padding, padding)

        if (labelsClickables) {
            linearLayout.isClickable = true
            linearLayout.setOnClickListener {
                listenerOnLabelClick?.onClickLabel(labelView as LLabelView)
            }
        }

        mTextView = labelView.findViewById(R.id.tvLabel)
        mTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        mTextView?.setTextColor(textColor)

        val imageView = labelView.findViewById<ImageView>(R.id.ivCross)

        if (showCross) {
            imageView.setImageResource(iconCross)
            imageView.setOnClickListener {
                listenerOnCrossClick?.onClickCross(labelView as LLabelView)
            }
        } else {
            imageView.visibility = View.GONE
        }

    }

    /**
     * Set a callback listener when the cross icon is clicked.
     *
     * @param listener Callback instance.
     */
    fun setOnClickCrossListener(listener: OnClickCrossListener) {
        this.listenerOnCrossClick = listener
    }

    /**
     * Interface for a callback listener when the cross icon is clicked.
     */
    interface OnClickCrossListener {

        /**
         * Call when the cross icon is clicked.
         */
        fun onClickCross(LLabelView: LLabelView)
    }

    /**
     * Set a callback listener when the [LLabelView] is clicked.
     *
     * @param listener Callback instance.
     */
    fun setOnLabelClickListener(listener: OnLabelClickListener) {
        this.listenerOnLabelClick = listener
    }

    /**
     * Interface for a callback listener when the [LLabelView] is clicked.
     * Container Activity/Fragment must implement this interface.
     */
    interface OnLabelClickListener {

        /**
         * Call when the [LLabelView] is clicked.
         */
        fun onClickLabel(LLabelView: LLabelView)
    }
}
