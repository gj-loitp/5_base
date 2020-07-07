package com.views.dialog.prettydialog

/**
 * Created by www.muathu@gmail.com on 1/4/2018.
 */

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.R
import com.core.utilities.LUIUtil
import java.util.*

class PrettyDialog(internal var context: Context) : AppCompatDialog(context) {
    internal var defaultIconTint: Int = R.color.colorPrimary
    internal var resources: Resources
    internal var llContent: LinearLayout? = null
    internal var llButtons: LinearLayout? = null
    internal var ivIcon: ImageView? = null
    internal var closeRotationAnimation: RotateAnimation? = null
    internal var iconAnimation = true
    internal var tvTitle: TextView? = null
    internal var tvMessage: TextView? = null
    internal var typeface: Typeface? = null
    internal var thisDialog: PrettyDialog

    private val prettyDialogButtonList = ArrayList<PrettyDialogButton>()

    init {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.l_dialog_pretty_dialog)
        setCancelable(true)
        resources = context.resources
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val displayMetrics = resources.displayMetrics
        val pxWidth = displayMetrics.widthPixels.toFloat()
        window?.setLayout((pxWidth * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.attributes?.windowAnimations = R.style.pdlg_default_animation
        thisDialog = this
        setupviewsBase()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupviewsBase() {
        llContent = findViewById(R.id.ll_content)
        llButtons = findViewById(R.id.ll_buttons)
        ivIcon = findViewById(R.id.iv_icon)

        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, resources.getDimensionPixelSize(R.dimen.pdlg_icon_size) / 2, 0, 0)

        llContent?.let {
            it.layoutParams = lp
            it.setPadding(0, (1.25 * resources.getDimensionPixelSize(R.dimen.pdlg_icon_size) / 2).toInt(),
                    0, resources.getDimensionPixelSize(R.dimen.pdlg_space))
        }

        closeRotationAnimation = RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        closeRotationAnimation?.let {
            it.duration = 300
            it.repeatCount = Animation.ABSOLUTE
            it.interpolator = DecelerateInterpolator()
            it.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                }

                override fun onAnimationEnd(animation: Animation) {
                    thisDialog.dismiss()
                }

                override fun onAnimationRepeat(animation: Animation) {
                }
            })
        }

        ivIcon?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.alpha = 0.7f
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    v.alpha = 1.0f
                    if (iconAnimation) {
                        v.startAnimation(closeRotationAnimation)
                    }
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
        tvTitle = findViewById(R.id.tvTitle)
        tvTitle?.visibility = View.GONE
        tvMessage = findViewById(R.id.tv_message)
        tvMessage?.visibility = View.GONE
    }

    fun addButton(text: String, textColor: Int?, backgroundColor: Int?,
            /*BUTTON_TYPE type,*/ callback: Runnable): PrettyDialog {
        val button = PrettyDialogButton(context, text, textColor, backgroundColor, typeface, /*type,*/ callback)
        prettyDialogButtonList.add(button)
        val margin = resources.getDimensionPixelSize(R.dimen.pdlg_space)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(margin, margin, margin, 0)
        button.layoutParams = lp
        llButtons?.addView(button)
        return this
    }

    fun setTitle(text: String): PrettyDialog {
        if (text.trim { it <= ' ' }.length > 0) {
            tvTitle?.visibility = View.VISIBLE
            tvTitle?.text = text
        } else {
            tvTitle?.visibility = View.GONE
        }
        return this
    }

    fun setTitleColor(color: Int?): PrettyDialog {
        tvTitle?.setTextColor(ContextCompat.getColor(context, color ?: R.color.black))
        return this
    }

    fun setMessage(text: String): PrettyDialog {
        if (text.trim { it <= ' ' }.length > 0) {
            tvMessage?.visibility = View.VISIBLE
            tvMessage?.text = text
        } else {
            tvMessage?.visibility = View.GONE
        }
        return this
    }

    fun setMessageColor(color: Int?): PrettyDialog {
        tvMessage?.setTextColor(ContextCompat.getColor(context, color ?: R.color.black))
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setIcon(icon: Int?): PrettyDialog {
        ivIcon?.setImageResource(icon ?: R.drawable.ic_close_black_48dp)
        iconAnimation = false
        ivIcon?.setOnTouchListener(null)
        return this
    }

    fun setIconTint(color: Int?): PrettyDialog {
        ivIcon?.setColorFilter(ContextCompat.getColor(context, color
                ?: defaultIconTint), PorterDuff.Mode.MULTIPLY)
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setIconCallback(callback: Runnable?): PrettyDialog {
        ivIcon?.setOnTouchListener(null)
        callback?.let {
            ivIcon?.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.7f
                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_UP -> {
                        v.alpha = 1.0f
                        callback.run()
                        return@setOnTouchListener true
                    }
                    else -> return@setOnTouchListener false
                }
            }
        }
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setIcon(icon: Int?, iconTint: Int?, callback: Runnable?): PrettyDialog {
        iconAnimation = false
        ivIcon?.let {
            it.setImageResource(icon ?: R.drawable.ic_close_black_48dp)
            it.setColorFilter(ContextCompat.getColor(context, iconTint
                    ?: defaultIconTint), PorterDuff.Mode.MULTIPLY)
            it.setOnTouchListener(null)
        }
        callback?.let {
            ivIcon?.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.7f
                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_UP -> {
                        v.alpha = 1.0f
                        callback.run()
                        return@setOnTouchListener true
                    }
                    else -> return@setOnTouchListener false
                }
            }
        }
        return this
    }

    fun setTypeface(tf: Typeface): PrettyDialog {
        typeface = tf
        tvTitle?.typeface = tf
        tvMessage?.typeface = tf
        llButtons?.let {
            for (i in 0 until it.childCount) {
                val button = it.getChildAt(i) as PrettyDialogButton
                button.setTypeface(tf)
                button.requestLayout()
            }
        }
        return this
    }

    fun setAnimationEnabled(enabled: Boolean): PrettyDialog {
        if (enabled) {
            window?.attributes?.windowAnimations = R.style.pdlg_default_animation
        } else {
            window?.attributes?.windowAnimations = R.style.pdlg_no_animation
        }
        return this
    }

    fun setTextSizeTitle(typedValue: Int, size: Int): PrettyDialog {
        if (tvTitle == null) {
            return this
        }
        LUIUtil.setTextSize(tvTitle, typedValue, size)
        return this
    }

    fun setTextSizeMsg(typedValue: Int, size: Int): PrettyDialog {
        if (tvMessage == null) {
            return this
        }
        LUIUtil.setTextSize(tvMessage, typedValue, size)
        return this
    }

    fun setTextSizeButton(typedValue: Int, size: Int): PrettyDialog {
        if (prettyDialogButtonList.isNotEmpty()) {
            for (prettyDialogButton in prettyDialogButtonList) {
                prettyDialogButton.setTextSize(typedValue, size)
            }
        }
        return this
    }
}