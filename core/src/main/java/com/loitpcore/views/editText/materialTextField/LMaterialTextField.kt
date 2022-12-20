package com.loitpcore.views.editText.materialTextField

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.loitpcore.R
import com.loitp.core.utilities.LAppResource

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class LMaterialTextField : FrameLayout {
    private lateinit var inputMethodManager: InputMethodManager
    var label: TextView? = null
    var card: View? = null
    var image: ImageView? = null
    var editText: EditText? = null
    var editTextLayout: ViewGroup? = null
    private var labelTopMargin = -1
    var isExpanded = false
    private var ANIMATION_DURATION = -1
    private var OPEN_KEYBOARD_ON_FOCUS = true
    private var labelColor = -1
    private var imageDrawableId = -1
    private var cardCollapsedHeight = -1
    private var hasFocus = false
    private var backgroundColor = -1
    private var reducedScale = 0.2f

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        handleAttributes(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        handleAttributes(context, attrs)
        init()
    }

    protected fun init() {
        inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun toggle() {
        if (isExpanded) {
            reduce()
        } else {
            expand()
        }
    }

    fun reduce() {
        if (isExpanded) {
            val heightInitial =
                context.resources.getDimensionPixelOffset(R.dimen.mtf_cardHeight_final)
            label?.let {
                ViewCompat.animate(it)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationY(0f).duration = ANIMATION_DURATION.toLong()
            }
            image?.let {
                ViewCompat.animate(it)
                    .alpha(0f)
                    .scaleX(0.4f)
                    .scaleY(0.4f).duration = ANIMATION_DURATION.toLong()
            }
            editText?.let {
                ViewCompat.animate(it)
                    .alpha(1f)
                    .setUpdateListener { view ->
                        val value = ViewCompat.getAlpha(view) // percentage
                        card?.layoutParams?.height =
                            (value * (heightInitial - cardCollapsedHeight) + cardCollapsedHeight).toInt()
                        card?.requestLayout()
                    }
                    .setDuration(ANIMATION_DURATION.toLong())
                    .setListener(object : ViewPropertyAnimatorListener {
                        override fun onAnimationStart(view: View) {
                            if (isExpanded) {
                                it.visibility = View.VISIBLE
                            }
                        }

                        override fun onAnimationEnd(view: View) {
                            if (!isExpanded) {
                                it.visibility = View.INVISIBLE
                            }
                        }

                        override fun onAnimationCancel(view: View) {}
                    })
            }
            card?.let {
                ViewCompat.animate(it)
                    .scaleY(reducedScale).duration = ANIMATION_DURATION.toLong()
            }
            editText?.let {
                if (it.hasFocus()) {
                    inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                    it.clearFocus()
                }
            }
            isExpanded = false
        }
    }

    fun expand() {
        if (!isExpanded) {
            editText?.let {
                ViewCompat.animate(it)
                    .alpha(1f).duration = ANIMATION_DURATION.toLong()
            }
            card?.let {
                ViewCompat.animate(it)
                    .scaleY(1f).duration = ANIMATION_DURATION.toLong()
            }
            label?.let {
                ViewCompat.animate(it)
                    .alpha(0.4f)
                    .scaleX(0.7f)
                    .scaleY(0.7f)
                    .translationY((-labelTopMargin).toFloat()).duration =
                    ANIMATION_DURATION.toLong()
            }
            image?.let {
                ViewCompat.animate(it)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f).duration = ANIMATION_DURATION.toLong()
            }
            editText?.requestFocus()
            if (OPEN_KEYBOARD_ON_FOCUS) {
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }
            isExpanded = true
        }
    }

    override fun setBackgroundColor(color: Int) {
        this.backgroundColor = color
    }

    @Suppress("unused")
    fun getBackgroundColor(): Int {
        return this.backgroundColor
    }

    @Suppress("unused")
    fun setHasFocus(hasFocus: Boolean) {
        this.hasFocus = hasFocus

        if (hasFocus) {
            expand()
            editText?.postDelayed({
                editText?.requestFocusFromTouch()
                inputMethodManager.showSoftInput(editText, 0)
            }, 300)
        } else {
            reduce()
        }
    }

    protected fun handleAttributes(context: Context, attrs: AttributeSet) {
        try {
            val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.LMaterialTextField)

            run {
                ANIMATION_DURATION = styledAttrs.getInteger(
                    R.styleable.LMaterialTextField_mtf_animationDuration,
                    400
                )
            }
            run {
                OPEN_KEYBOARD_ON_FOCUS = styledAttrs.getBoolean(
                    R.styleable.LMaterialTextField_mtf_openKeyboardOnFocus,
                    false
                )
            }
            run {
                labelColor = styledAttrs.getColor(R.styleable.LMaterialTextField_mtf_labelColor, -1)
            }
            run {
                imageDrawableId =
                    styledAttrs.getResourceId(R.styleable.LMaterialTextField_mtf_image, -1)
            }
            run {
                cardCollapsedHeight = styledAttrs.getDimensionPixelOffset(
                    R.styleable.LMaterialTextField_mtf_cardCollapsedHeight,
                    context.resources.getDimensionPixelOffset(R.dimen.mtf_cardHeight_initial)
                )
            }
            run {
                hasFocus =
                    styledAttrs.getBoolean(R.styleable.LMaterialTextField_mtf_hasFocus, false)
            }
            run {
                backgroundColor =
                    styledAttrs.getColor(R.styleable.LMaterialTextField_mtf_backgroundColor, -1)
            }

            styledAttrs.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun findEditTextChild(): EditText? {
        return if (childCount > 0 && getChildAt(0) is EditText) {
            getChildAt(0) as EditText
        } else null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        editText = findEditTextChild()
        if (editText == null) {
            return
        }

        addView(
            LayoutInflater.from(context).inflate(R.layout.layout_material_text_field, this, false)
        )

        editTextLayout = findViewById<View>(R.id.mtf_editTextLayout) as ViewGroup
        removeView(editText)
        editTextLayout?.addView(editText)

        label = findViewById<View>(R.id.mtf_label) as TextView
        ViewCompat.setPivotX(label, 0f)
        ViewCompat.setPivotY(label, 0f)

        editText?.hint.let {
            label?.text = it
            editText?.hint = ""
        }
        card = findViewById(R.id.mtf_card)

        if (backgroundColor != -1) {
            card?.setBackgroundColor(backgroundColor)
        }

        val expandedHeight = context.resources.getDimensionPixelOffset(R.dimen.mtf_cardHeight_final)
        val reducedHeight = cardCollapsedHeight

        reducedScale = (reducedHeight * 1.0 / expandedHeight).toFloat()
        ViewCompat.setScaleY(card, reducedScale)
        ViewCompat.setPivotY(card, expandedHeight.toFloat())

        image = findViewById<ImageView>(R.id.mtf_image)
        ViewCompat.setAlpha(image, 0f)
        ViewCompat.setScaleX(image, 0.4f)
        ViewCompat.setScaleY(image, 0.4f)

        ViewCompat.setAlpha(editText, 0f)
        editText?.setBackgroundColor(Color.TRANSPARENT)

        labelTopMargin = LayoutParams::class.java.cast(label?.layoutParams)!!.topMargin

        customizeFromAttributes()

        this.setOnClickListener { toggle() }

        setHasFocus(hasFocus)
    }

    private fun customizeFromAttributes() {
        if (labelColor != -1) {
            this.label?.setTextColor(labelColor)
        }
        if (imageDrawableId != -1) {
            this.image?.setImageDrawable(LAppResource.getDrawable(imageDrawableId))
        }
    }
}
