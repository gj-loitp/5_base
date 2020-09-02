package com.views.dialog.prettydialog

/**
 * Created by www.muathu@gmail.com on 1/4/2018.
 */

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.R
import com.core.utilities.LUIUtil
import kotlin.math.min

internal class PrettyDialogButton(
        var mContext: Context,
        var text: String,
        var textColor: Int?,
        var backgroundColor:/*PrettyDialog.BUTTON_TYPE background_type = PrettyDialog.BUTTON_TYPE.BORDER;*/
        Int?,
        var tf: Typeface?,
        /*PrettyDialog.BUTTON_TYPE type,*/
        var callback: Runnable?) : LinearLayout(mContext) {
    var defaultBackgroundColor: Int = R.color.black
    private var defaultTextColor: Int = R.color.white
    var tv: TextView? = null
    var iv: ImageView? = null

    init {
        init()
    }/*this.background_type = type;*/

    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.l_dialog_pretty_button, this)
        tv = findViewById<TextView>(R.id.tv_button)
        tv?.let {
            it.text = text
            if (textColor == null) {
                it.setTextColor(ContextCompat.getColor(mContext, defaultTextColor))
            } else {
                it.setTextColor(ContextCompat.getColor(mContext, textColor!!))
            }
        }
        tf?.let {
            tv?.typeface = it
        }
        setBackground()
        setOnClickListener { v ->
            callback?.let {
                v.postDelayed({ it.run() }, 150)
            }
        }
        /*setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.7f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setAlpha(1.0f);
                        if(callback!=null) {
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onClick();
                                }
                            }, 150);
                        }
                        return true;
                }
                return false;
            }
        });*/
    }

    /*private void setBackgroundType(PrettyDialog.BUTTON_TYPE type) {
        background_type = type;
        setBackground();
    }*/

    fun setTypeface(tf: Typeface) {
        this.tf = tf
        tv?.typeface = tf
    }

    private fun setBackground() {
        if (backgroundColor == null) {
            setBackgroundDrawable(makeSelector(ContextCompat.getColor(mContext, defaultBackgroundColor)))
        } else {
            setBackgroundDrawable(makeSelector(ContextCompat.getColor(mContext, backgroundColor!!)))
        }
    }

    private fun getLightenColor(color: Int): Int {
        val fraction = 0.2
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = min(red + red * fraction, 255.0).toInt()
        green = min(green + green * fraction, 255.0).toInt()
        blue = min(blue + blue * fraction, 255.0).toInt()
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    private fun makeSelector(color: Int): StateListDrawable {
        val res = StateListDrawable()
        res.setExitFadeDuration(150)
        val pressedDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(getLightenColor(color), getLightenColor(color)))
        pressedDrawable.cornerRadius = resources.getDimensionPixelSize(R.dimen.pdlg_corner_radius).toFloat()
        val defaultDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(color, color))
        defaultDrawable.cornerRadius = resources.getDimensionPixelSize(R.dimen.pdlg_corner_radius).toFloat()
        res.addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
        res.addState(intArrayOf(), defaultDrawable)
        return res
    }

    fun setTextSize(size: Float) {
        LUIUtil.setTextSize(textView = tv, size = size)
    }
}
