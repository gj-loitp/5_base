package com.views.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.R
import com.github.mmin18.widget.RealtimeBlurView

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LActionBar : RelativeLayout {
    var ivIconBack: ImageView? = null
    var ivIconMenu: ImageView? = null
    var tvTitle: TextView? = null
    var realtimeBlurView: RealtimeBlurView? = null
    private var shadowView: View? = null
    private var callback: Callback? = null

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
        View.inflate(context, R.layout.view_l_action_bar, this)

        this.ivIconBack = findViewById(R.id.iv_icon_back)
        this.ivIconMenu = findViewById(R.id.iv_icon_menu)
        this.tvTitle = findViewById(R.id.tvTitle)
        this.realtimeBlurView = findViewById(R.id.blur_view)
        this.shadowView = findViewById(R.id.shadow_view)

        ivIconBack?.setOnClickListener { v ->
            callback?.onClickBack(v)
        }
        ivIconMenu?.setOnClickListener { v ->
            callback?.onClickMenu(v)
        }
    }

    interface Callback {
        fun onClickBack(view: View)

        fun onClickMenu(view: View)
    }

    fun setOnClickBack(onClickBack: Callback?) {
        this.callback = onClickBack
    }

    fun setTvTitle(title: String) {
        tvTitle?.text = title
    }

    fun hideBackIcon() {
        ivIconBack?.visibility = View.GONE
    }

    fun inviBackIcon() {
        ivIconBack?.visibility = View.INVISIBLE
    }

    fun inviMenuIcon() {
        ivIconMenu?.visibility = View.INVISIBLE
    }

    fun hideMenuIcon() {
        ivIconMenu?.visibility = View.GONE
    }

    fun showMenuIcon() {
        ivIconMenu?.visibility = View.VISIBLE
    }

    fun setTvTitlePositionLeft() {
        tvTitle?.gravity = Gravity.START or Gravity.CENTER_VERTICAL
    }

    fun setTvTitlePositionCenter() {
        tvTitle?.gravity = Gravity.CENTER
    }

    fun setImageMenuIcon(drawableRes: Int) {
        ivIconMenu?.setImageResource(drawableRes)
    }

    fun setImageBackIcon(drawableRes: Int) {
        ivIconBack?.setImageResource(drawableRes)
    }

    fun hideBlurView() {
        realtimeBlurView?.visibility = View.GONE
    }

    fun setBlurRadius(radius: Float) {
        realtimeBlurView?.setBlurRadius(radius)
    }

    fun setBlurOverlayColor(color: Int) {
        realtimeBlurView?.setOverlayColor(color)
    }

    fun hideShadowView() {
        shadowView?.visibility = View.GONE
    }

    fun showShadowView() {
        shadowView?.visibility = View.VISIBLE
    }
}
