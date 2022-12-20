package com.loitpcore.views.card

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.loitpcore.R
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LCardView : RelativeLayout {

    interface Callback {
        fun onClickRoot(v: View)

        fun onLongClickRoot(v: View)

        fun onClickText(v: View)

        fun onLongClickText(v: View)
    }

    lateinit var cardView: CardView
    lateinit var imageView: ImageView
    lateinit var textView: TextView

    var callback: Callback? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_card_view, this)

        cardView = findViewById(R.id.cardView)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        cardView.setOnClickListener { v ->
            callback?.onClickRoot(v)
        }
        cardView.setOnLongClickListener { v ->
            callback?.onLongClickRoot(v)
            true
        }
        textView.setOnClickListener { v ->
            callback?.onClickText(v)
        }
        textView.setOnLongClickListener { v ->
            callback?.onLongClickText(v)
            true
        }
    }

    fun setHeight(px: Int) {
        cardView.apply {
            layoutParams.height = px
            requestLayout()
        }
    }

    fun setWidth(px: Int) {
        cardView.apply {
            layoutParams.width = px
            requestLayout()
        }
    }

    fun setRadius(px: Float) {
        cardView.radius = px
    }

    @Suppress("unused")
    fun setCardBackground(color: Int) {
        cardView.setCardBackgroundColor(color)
    }

    fun setCardElevation(elevation: Float) {
        cardView.cardElevation = elevation
        LUIUtil.setMargins(
            view = cardView,
            leftPx = elevation.toInt(),
            topPx = elevation.toInt(),
            rightPx = elevation.toInt(),
            bottomPx = elevation.toInt() * 2
        )
    }

    fun setImg(url: String) {
        LImageUtil.load(context = context, any = url, imageView = imageView)
    }

    fun setText(s: String) {
        textView.text = s
    }

    fun setText(res: Int) {
        textView.text = context.getString(res)
    }

    fun setTextColor(color: Int) {
        textView.setTextColor(color)
    }

    fun setTextShadow(color: Int) {
        textView.setTextColor(color)
    }
}
