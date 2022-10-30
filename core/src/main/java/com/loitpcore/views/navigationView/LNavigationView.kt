package com.loitpcore.views.navigationView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.daimajia.androidanimations.library.Techniques
import com.loitpcore.R
import com.loitpcore.core.utilities.LAnimationUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LNavigationView : RelativeLayout, View.OnClickListener {
    @Suppress("unused")
    var ivPrev: ImageView? = null
    @Suppress("unused")
    var ivNext: ImageView? = null
    @Suppress("unused")
    var tv: TextView? = null

    private var stringList = ArrayList<String>()
    private var currentIndex = 0
    var colorOn = Color.BLACK
    var colorOff = Color.GRAY
    @Suppress("unused")
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

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_navigation, this)

        ivPrev = findViewById(R.id.ivPrev)
        ivNext = findViewById(R.id.ivNext)
        tv = findViewById(R.id.textView)

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
        currentIndex = 0
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
            setEnableController(imageView = ivPrev, isEnable = false)
            setEnableController(imageView = ivNext, isEnable = false)
        } else {
            when {
                currentIndex <= 0 -> {
                    setEnableController(imageView = ivPrev, isEnable = false)
                    setEnableController(imageView = ivNext, isEnable = true)
                }
                currentIndex >= size - 1 -> {
                    setEnableController(imageView = ivPrev, isEnable = true)
                    setEnableController(imageView = ivNext, isEnable = false)
                }
                else -> {
                    setEnableController(imageView = ivPrev, isEnable = true)
                    setEnableController(imageView = ivNext, isEnable = true)
                }
            }
        }
        nvCallback?.onIndexChange(index = currentIndex, s = s)
    }

    override fun onClick(view: View) {
        if (view === ivPrev) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currentIndex--
            updateUI()
        } else if (view === ivNext) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
            }
            currentIndex++
            updateUI()
        }
    }

    @Suppress("unused")
    fun getStringList(): ArrayList<String> {
        return stringList
    }

    @Suppress("unused")
    fun getCurrentIndex(): Int {
        return currentIndex
    }
}
