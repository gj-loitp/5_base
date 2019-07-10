package vn.loitp.views.bottombar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.core.utilities.LAnimationUtil
import com.daimajia.androidanimations.library.Techniques
import loitp.core.R
import vn.loitp.views.realtimeblurview.RealtimeBlurView

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LBottomBar : RelativeLayout, View.OnClickListener {
    val TAG = javaClass.simpleName
    var blurView: RealtimeBlurView? = null
    var llIcon0: LinearLayout? = null
    var llIcon1: LinearLayout? = null
    var llIcon2: LinearLayout? = null
    var llIcon3: LinearLayout? = null
    var llIcon4: LinearLayout? = null
    var llIcon5: LinearLayout? = null
    var ivIcon0: ImageView? = null
    var ivIcon1: ImageView? = null
    var ivIcon2: ImageView? = null
    var ivIcon3: ImageView? = null
    var ivIcon4: ImageView? = null
    var ivIcon5: ImageView? = null
    var tvIcon0: TextView? = null
    var tvIcon1: TextView? = null
    var tvIcon2: TextView? = null
    var tvIcon3: TextView? = null
    var tvIcon4: TextView? = null
    var tvIcon5: TextView? = null
    var previousPos: Int = 0
    var currentPos: Int = 0

    var colorIvOn = R.color.colorPrimary
        set(colorOn) {
            field = colorOn
            refreshUI()
        }
    var colorIvOff = R.color.Black
        set(colorOff) {
            field = colorOff
            refreshUI()
        }
    private var techniques: Techniques? = Techniques.Pulse

    var paddingOnInDp = 5
    var paddingOffInDp = 25

    private var callback: Callback? = null

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
        View.inflate(context, R.layout.view_l_bottom_bar, this)
        this.blurView = findViewById<RealtimeBlurView>(R.id.real_time_blur_view)
        this.llIcon0 = findViewById<LinearLayout>(R.id.ll_icon_0)
        this.llIcon1 = findViewById<LinearLayout>(R.id.ll_icon_1)
        this.llIcon2 = findViewById<LinearLayout>(R.id.ll_icon_2)
        this.llIcon3 = findViewById<LinearLayout>(R.id.ll_icon_3)
        this.llIcon4 = findViewById<LinearLayout>(R.id.ll_icon_4)
        this.llIcon5 = findViewById<LinearLayout>(R.id.ll_icon_5)
        this.ivIcon0 = findViewById<ImageView>(R.id.iv_icon_0)
        this.ivIcon1 = findViewById<ImageView>(R.id.iv_icon_1)
        this.ivIcon2 = findViewById<ImageView>(R.id.iv_icon_2)
        this.ivIcon3 = findViewById<ImageView>(R.id.iv_icon_3)
        this.ivIcon4 = findViewById<ImageView>(R.id.iv_icon_4)
        this.ivIcon5 = findViewById<ImageView>(R.id.iv_icon_5)
        this.tvIcon0 = findViewById<TextView>(R.id.tv_icon_0)
        this.tvIcon1 = findViewById<TextView>(R.id.tv_icon_1)
        this.tvIcon2 = findViewById<TextView>(R.id.tv_icon_2)
        this.tvIcon3 = findViewById<TextView>(R.id.tv_icon_3)
        this.tvIcon4 = findViewById<TextView>(R.id.tv_icon_4)
        this.tvIcon5 = findViewById<TextView>(R.id.tv_icon_5)
        llIcon0!!.setOnClickListener(this)
        llIcon1!!.setOnClickListener(this)
        llIcon2!!.setOnClickListener(this)
        llIcon3!!.setOnClickListener(this)
        llIcon4!!.setOnClickListener(this)
        llIcon5!!.setOnClickListener(this)
        updateView(ivIcon0, tvIcon0)
    }

    fun setCount(count: Int) {
        if (count !in 0 until 7) {
            throw IllegalArgumentException("Error value of count number, value must between 0 and 7.")
        }
        if (count == 0) {
            llIcon0!!.visibility = View.GONE
            llIcon1!!.visibility = View.GONE
            llIcon2!!.visibility = View.GONE
            llIcon3!!.visibility = View.GONE
            llIcon4!!.visibility = View.GONE
            llIcon5!!.visibility = View.GONE
        } else if (count == 1) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.GONE
            llIcon2!!.visibility = View.GONE
            llIcon3!!.visibility = View.GONE
            llIcon4!!.visibility = View.GONE
            llIcon5!!.visibility = View.GONE
        } else if (count == 2) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.VISIBLE
            llIcon2!!.visibility = View.GONE
            llIcon3!!.visibility = View.GONE
            llIcon4!!.visibility = View.GONE
            llIcon5!!.visibility = View.GONE
        } else if (count == 3) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.VISIBLE
            llIcon2!!.visibility = View.VISIBLE
            llIcon3!!.visibility = View.GONE
            llIcon4!!.visibility = View.GONE
            llIcon5!!.visibility = View.GONE
        } else if (count == 4) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.VISIBLE
            llIcon2!!.visibility = View.VISIBLE
            llIcon3!!.visibility = View.VISIBLE
            llIcon4!!.visibility = View.GONE
            llIcon5!!.visibility = View.GONE
        } else if (count == 5) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.VISIBLE
            llIcon2!!.visibility = View.VISIBLE
            llIcon3!!.visibility = View.VISIBLE
            llIcon4!!.visibility = View.VISIBLE
            llIcon5!!.visibility = View.GONE
        } else if (count == 6) {
            llIcon0!!.visibility = View.VISIBLE
            llIcon1!!.visibility = View.VISIBLE
            llIcon2!!.visibility = View.VISIBLE
            llIcon3!!.visibility = View.VISIBLE
            llIcon4!!.visibility = View.VISIBLE
            llIcon5!!.visibility = View.VISIBLE
        }
    }

    fun setItem0(resImg: Int, name: String) {
        ivIcon0!!.setImageResource(resImg)
        tvIcon0!!.text = name
    }

    fun setItem1(resImg: Int, name: String) {
        ivIcon1!!.setImageResource(resImg)
        tvIcon1!!.text = name
    }

    fun setItem2(resImg: Int, name: String) {
        ivIcon2!!.setImageResource(resImg)
        tvIcon2!!.text = name
    }

    fun setItem3(resImg: Int, name: String) {
        ivIcon3!!.setImageResource(resImg)
        tvIcon3!!.text = name
    }

    fun setItem4(resImg: Int, name: String) {
        ivIcon4!!.setImageResource(resImg)
        tvIcon4!!.text = name
    }

    fun setItem5(resImg: Int, name: String) {
        ivIcon5!!.setImageResource(resImg)
        tvIcon5!!.text = name
    }

    override fun onClick(v: View) {
        if (v === llIcon0) {
            if (currentPos != PAGE_0) {
                previousPos = currentPos
                currentPos = PAGE_0
                onClickItem(currentPos)
                updateView(ivIcon0, tvIcon0)
            }
        } else if (v === llIcon1) {
            if (currentPos != PAGE_1) {
                previousPos = currentPos
                currentPos = PAGE_1
                onClickItem(currentPos)
                updateView(ivIcon1, tvIcon1)
            }
        } else if (v === llIcon2) {
            if (currentPos != PAGE_2) {
                previousPos = currentPos
                currentPos = PAGE_2
                onClickItem(currentPos)
                updateView(ivIcon2, tvIcon2)
            }
        } else if (v === llIcon3) {
            if (currentPos != PAGE_3) {
                previousPos = currentPos
                currentPos = PAGE_3
                onClickItem(currentPos)
                updateView(ivIcon3, tvIcon3)
            }
        } else if (v === llIcon4) {
            if (currentPos != PAGE_4) {
                previousPos = currentPos
                currentPos = PAGE_4
                onClickItem(currentPos)
                updateView(ivIcon4, tvIcon4)
            }
        } else if (v === llIcon5) {
            if (currentPos != PAGE_5) {
                previousPos = currentPos
                currentPos = PAGE_5
                onClickItem(currentPos)
                updateView(ivIcon5, tvIcon5)
            }
        }
    }

    fun setTechniques(techniques: Techniques) {
        this.techniques = techniques
    }

    fun setColorTextView(colorRes: Int) {
        tvIcon0!!.setTextColor(ContextCompat.getColor(context, colorRes))
        tvIcon1!!.setTextColor(ContextCompat.getColor(context, colorRes))
        tvIcon2!!.setTextColor(ContextCompat.getColor(context, colorRes))
        tvIcon3!!.setTextColor(ContextCompat.getColor(context, colorRes))
        tvIcon4!!.setTextColor(ContextCompat.getColor(context, colorRes))
        tvIcon5!!.setTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun refreshUI() {
        if (currentPos == PAGE_0) {
            updateView(ivIcon0, tvIcon0)
        } else if (currentPos == PAGE_1) {
            updateView(ivIcon1, tvIcon1)
        } else if (currentPos == PAGE_2) {
            updateView(ivIcon2, tvIcon2)
        } else if (currentPos == PAGE_3) {
            updateView(ivIcon3, tvIcon3)
        } else if (currentPos == PAGE_4) {
            updateView(ivIcon4, tvIcon4)
        } else if (currentPos == PAGE_5) {
            updateView(ivIcon5, tvIcon5)
        }
    }

    private fun updateView(imageView: ImageView?, textView: TextView?) {
        if (techniques != null) {
            LAnimationUtil.play(imageView, techniques)
            LAnimationUtil.play(textView, techniques)
        }
        ivIcon0!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        ivIcon1!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        ivIcon2!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        ivIcon3!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        ivIcon4!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        ivIcon5!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOff))
        tvIcon0!!.visibility = View.GONE
        tvIcon1!!.visibility = View.GONE
        tvIcon2!!.visibility = View.GONE
        tvIcon3!!.visibility = View.GONE
        tvIcon4!!.visibility = View.GONE
        tvIcon5!!.visibility = View.GONE
        ivIcon0!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        ivIcon1!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        ivIcon2!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        ivIcon3!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        ivIcon4!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        ivIcon5!!.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        imageView!!.setColorFilter(ContextCompat.getColor(context, this.colorIvOn))
        imageView.setPadding(paddingOnInDp, paddingOnInDp, paddingOnInDp, paddingOnInDp)
        textView!!.visibility = View.VISIBLE
    }

    private fun onClickItem(pos: Int) {
        if (callback != null) {
            callback!!.OnClickItem(pos)
        }
    }

    interface Callback {
        fun OnClickItem(position: Int)
    }

    fun setOnItemClick(callback: Callback) {
        this.callback = callback
    }

    fun setPerformItemClick(position: Int) {
        //LLog.d(TAG, "setPerformItemClick " + position);
        previousPos = currentPos
        currentPos = position
        when (position) {
            PAGE_0 -> {
                onClickItem(PAGE_0)
                updateView(ivIcon0, tvIcon0)
            }
            PAGE_1 -> {
                onClickItem(PAGE_1)
                updateView(ivIcon1, tvIcon1)
            }
            PAGE_2 -> {
                onClickItem(PAGE_2)
                updateView(ivIcon2, tvIcon2)
            }
            PAGE_3 -> {
                onClickItem(PAGE_3)
                updateView(ivIcon3, tvIcon3)
            }
            PAGE_4 -> {
                onClickItem(PAGE_4)
                updateView(ivIcon4, tvIcon4)
            }
            PAGE_5 -> {
                onClickItem(PAGE_5)
                updateView(ivIcon5, tvIcon5)
            }
        }
    }

    companion object {
        val PAGE_NONE = -1
        val PAGE_0 = 0
        val PAGE_1 = 1
        val PAGE_2 = 2
        val PAGE_3 = 3
        val PAGE_4 = 4
        val PAGE_5 = 5
    }
}