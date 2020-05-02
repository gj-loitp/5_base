package com.views.card

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.cardview.widget.CardView
import com.R

import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LCardView : RelativeLayout {
    private val TAG = javaClass.simpleName
    lateinit var cardView: CardView
    lateinit var iv: ImageView
    lateinit var tv: TextView

    var callback: Callback? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_card_view, this)
        cardView = findViewById(R.id.card_view)
        tv = findViewById(R.id.textView)
        iv = findViewById(R.id.imageView)

        cardView.setOnClickListener { v ->
            callback?.onClickRoot(v)
        }

        cardView.setOnLongClickListener { v ->
            callback?.onLongClickRoot(v)
            true
        }
        tv.setOnClickListener { v ->
            callback?.onClickText(v)
        }

        tv.setOnLongClickListener { v ->
            callback?.onLongClickText(v)
            true
        }
    }

    fun setHeight(px: Int) {
        cardView.let {
            it.layoutParams.height = px
            it.requestLayout()
        }
    }

    fun setWidth(px: Int) {
        cardView.let {
            it.layoutParams.width = px
            it.requestLayout()
        }
    }

    fun setRadius(px: Float) {
        cardView.radius = px
    }

    fun setCardBackground(color: Int) {
        cardView.setCardBackgroundColor(color)
    }

    fun setCardElevation(elevation: Float) {
        cardView.cardElevation = elevation
        LUIUtil.setMargins(cardView, elevation.toInt(), elevation.toInt(), elevation.toInt(), elevation.toInt() * 2)
    }

    fun setImg(url: String) {
        iv.let {
            LImageUtil.load(context, url, it)
        }
    }

    fun setText(s: String) {
        tv.text = s
    }

    fun setText(res: Int) {
        tv.text = context.getString(res)
    }

    fun setTextColor(color: Int) {
        tv.setTextColor(color)
    }

    fun setTextShadow(color: Int) {
        tv.setTextColor(color)
    }

    interface Callback {
        fun onClickRoot(v: View)

        fun onLongClickRoot(v: View)

        fun onClickText(v: View)

        fun onLongClickText(v: View)
    }
}