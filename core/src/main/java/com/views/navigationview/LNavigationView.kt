package com.views.navigationview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.R
import com.core.utilities.LAnimationUtil
import com.daimajia.androidanimations.library.Techniques

class LNavigationView : RelativeLayout, View.OnClickListener {
    var ivPrev: ImageView? = null
    var ivNext: ImageView? = null
    var tv: TextView? = null

    private var stringList = ArrayList<String>()
    private var currenIndex = 0
    var colorOn = Color.BLACK
    var colorOff = Color.GRAY
    var isEnableAnimation = true

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

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_navigation, this)

        ivPrev = findViewById(R.id.ivPrev)
        ivNext = findViewById(R.id.ivNext)
        tv = findViewById(R.id.tv)

        ivPrev?.setOnClickListener(this)
        ivNext?.setOnClickListener(this)

        setEnableController(imageView = ivPrev, isEnable = false)
        setEnableController(imageView = ivNext, isEnable = false)
    }

    private fun setEnableController(imageView: ImageView?, isEnable: Boolean) {
        if (isEnable) {
            imageView?.let {
                it.isEnabled = true
                it.isClickable = true
                it.setColorFilter(colorOn)
            }
        } else {
            imageView?.let {
                it.isEnabled = false
                it.isClickable = false
                it.setColorFilter(colorOff)
            }
        }
    }

    fun setStringList(stringList: ArrayList<String>?) {
        if (stringList == null || stringList.isEmpty()) {
            return
        }
        currenIndex = 0
        this.stringList = stringList
        updateUI()
    }

    fun setCurrenIndex(index: Int) {
        if (stringList.isEmpty()) {
            return
        }
        if (index < 0 || index > stringList.size - 1) {
            return
        }
        currenIndex = index
        updateUI()
    }

    private fun updateUI() {
        val s = stringList[currenIndex]
        tv?.text = s
        if (isEnableAnimation) {
            LAnimationUtil.play(view = tv, techniques = Techniques.FlipInX)
        }
        val size = stringList.size
        if (size == 1) {
            setEnableController(imageView = ivPrev, isEnable = false)
            setEnableController(imageView = ivNext, isEnable = false)
        } else {
            when {
                currenIndex <= 0 -> {
                    setEnableController(imageView = ivPrev, isEnable = false)
                    setEnableController(imageView = ivNext, isEnable = true)
                }
                currenIndex >= size - 1 -> {
                    setEnableController(imageView = ivPrev, isEnable = true)
                    setEnableController(imageView = ivNext, isEnable = false)
                }
                else -> {
                    setEnableController(imageView = ivPrev, isEnable = true)
                    setEnableController(imageView = ivNext, isEnable = true)
                }
            }
        }
        nvCallback?.onIndexChange(index = currenIndex, s = s)
    }

    override fun onClick(view: View) {
        if (view === ivPrev) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currenIndex--
            updateUI()
        } else if (view === ivNext) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currenIndex++
            updateUI()
        }
    }

    fun getStringList(): ArrayList<String> {
        return stringList
    }

    fun getCurrenIndex(): Int {
        return currenIndex
    }
}
