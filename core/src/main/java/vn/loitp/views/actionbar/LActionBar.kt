package vn.loitp.views.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.core.utilities.LAnimationUtil
import com.daimajia.androidanimations.library.Techniques
import loitp.core.R
import vn.loitp.views.realtimeblurview.RealtimeBlurView

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LActionBar : RelativeLayout {
    private val TAG = javaClass.simpleName
    var ivIconBack: ImageView? = null
    var ivIconMenu: ImageView? = null
    var tvTitle: TextView? = null
    private var realtimeBlurView: RealtimeBlurView? = null
    private var shadowView: View? = null
    private var callback: Callback? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_action_bar, this)

        this.ivIconBack = findViewById<ImageView>(R.id.iv_icon_back)
        this.ivIconMenu = findViewById<ImageView>(R.id.iv_icon_menu)
        this.tvTitle = findViewById<TextView>(R.id.tv_title)
        this.realtimeBlurView = findViewById<RealtimeBlurView>(R.id.blur_view)
        this.shadowView = findViewById<View>(R.id.shadow_view)

        ivIconBack!!.setOnClickListener { v ->
            LAnimationUtil.play(v, Techniques.Pulse)
            if (callback != null) {
                callback!!.onClickBack()
            }
        }
        ivIconMenu!!.setOnClickListener { v ->
            LAnimationUtil.play(v, Techniques.Pulse)
            if (callback != null) {
                callback!!.onClickMenu()
            }
        }
    }

    interface Callback {
        fun onClickBack()

        fun onClickMenu()
    }

    fun setOnClickBack(onClickBack: Callback) {
        this.callback = onClickBack
    }

    fun setTvTitle(title: String) {
        tvTitle!!.text = title
    }

    fun hideBackIcon() {
        ivIconBack!!.visibility = View.GONE
    }

    fun inviBackIcon() {
        ivIconBack!!.visibility = View.INVISIBLE
    }

    fun hideMenuIcon() {
        ivIconMenu!!.visibility = View.GONE
    }

    fun showMenuIcon() {
        ivIconMenu!!.visibility = View.VISIBLE
    }

    fun setTvTitlePositionLeft() {
        tvTitle!!.gravity = Gravity.START or Gravity.CENTER_VERTICAL
    }

    fun setTvTitlePositionCenter() {
        tvTitle!!.gravity = Gravity.CENTER
    }

    fun setImageMenuIcon(drawableRes: Int) {
        ivIconMenu!!.setImageResource(drawableRes)
    }

    fun setImageBackIcon(drawableRes: Int) {
        ivIconBack!!.setImageResource(drawableRes)
    }

    fun hideBlurView() {
        realtimeBlurView!!.visibility = View.GONE
    }

    fun hideShadowView() {
        shadowView!!.visibility = View.GONE
    }
}