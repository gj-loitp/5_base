package com.views.navigationview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.R
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques

class LTextNavigationView : RelativeLayout, View.OnClickListener {
    var tvPrev: TextView? = null
    var tvNext: TextView? = null
    var tv: TextView? = null

    private var stringList = ArrayList<String>()
    private var currentIndex = 0
    var isEnableAnimation = true
    var colorOn = Color.BLACK
    var colorOff = Color.GRAY
    private var nvCallback: NVCallback? = null

    interface NVCallback {
        fun onIndexChange(index: Int, s: String?)
    }

    fun setNVCallback(nvCallback: NVCallback?) {
        this.nvCallback = nvCallback
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_navigation_text, this)
        tv = findViewById(R.id.textView)
        tvPrev = findViewById(R.id.tvPrev)
        tvNext = findViewById(R.id.tvNext)

        tvPrev?.setOnClickListener(this)
        tvNext?.setOnClickListener(this)
        setEnableController(textView = tvPrev, isEnable = false)
        setEnableController(textView = tvNext, isEnable = false)
    }

    private fun setEnableController(textView: TextView?, isEnable: Boolean) {
        if (isEnable) {
            textView?.let {
                it.isEnabled = true
                it.isClickable = true
                it.setTextColor(colorOn)
            }
        } else {
            textView?.let {
                it.isEnabled = false
                it.isClickable = false
                it.setTextColor(colorOff)
            }
        }
    }

    fun setStringList(stringList: ArrayList<String>?) {
        if (stringList.isNullOrEmpty()) {
            return
        }
        currentIndex = 0
        this.stringList = stringList
        updateUI()
    }

    fun setCurrentIndex(index: Int) {
        if (stringList.isEmpty()) {
            return
        }
        if (index < 0 || index > stringList.size - 1) {
            return
        }
        currentIndex = index
        updateUI()
    }

    private fun updateUI() {
        val s = stringList[currentIndex]
        tv?.text = s
        if (isEnableAnimation) {
            LAnimationUtil.play(view = tv, techniques = Techniques.FlipInX)
        }
        val size = stringList.size
        if (size == 1) {
            setEnableController(textView = tvPrev, isEnable = false)
            setEnableController(textView = tvNext, isEnable = false)
        } else {
            when {
                currentIndex <= 0 -> {
                    setEnableController(textView = tvPrev, isEnable = false)
                    setEnableController(textView = tvNext, isEnable = true)
                }
                currentIndex >= size - 1 -> {
                    setEnableController(textView = tvPrev, isEnable = true)
                    setEnableController(textView = tvNext, isEnable = false)
                }
                else -> {
                    setEnableController(textView = tvPrev, isEnable = true)
                    setEnableController(textView = tvNext, isEnable = true)
                }
            }
        }
        nvCallback?.onIndexChange(index = currentIndex, s = s)
    }

    override fun onClick(view: View) {
        if (view === tvPrev) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currentIndex--
            updateUI()
        } else if (view === tvNext) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currentIndex++
            updateUI()
        }
    }

    fun getStringList(): ArrayList<String> {
        return stringList
    }

    fun getCurrentIndex(): Int {
        return currentIndex
    }

    fun setTextPrev(prev: String?) {
        tvPrev?.text = prev
    }

    fun setTextNext(next: String?) {
        tvNext?.text = next
    }

    fun setTextSize(dpPrev: Float, dpText: Float, dpNext: Float) {
        LUIUtil.setTextSize(textView = tvPrev, size = dpPrev)
        LUIUtil.setTextSize(textView = tv, size = dpText)
        LUIUtil.setTextSize(textView = tvNext, size = dpNext)
    }
}
