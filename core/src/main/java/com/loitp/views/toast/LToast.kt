package com.loitp.views.toast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.loitp.R
import com.loitp.core.ext.LAppResource.application
import com.loitp.core.ext.addDrawableLTRB
import com.loitp.core.ext.getColor
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.isDebugMode
import com.loitp.core.ext.setDrawableTint

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LToast {
    @JvmStatic
    @SuppressLint("InflateParams")
    @JvmOverloads
    fun show(
        msg: String?,
        length: Int = Toast.LENGTH_SHORT,
        backgroundResColor: Int = R.color.red,
        textResColor: Int = R.color.white,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        if (msg.isNullOrEmpty()) {
            return
        }
        try {
            val inf =
                application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inf.inflate(R.layout.l_v_toast, null)
            val textView = layout.findViewById<AppCompatTextView>(R.id.tvLoading)
            textView.apply {
                text = msg
                setBackgroundColor(getColor(backgroundResColor))
                setTextColor(getColor(textResColor))
                setDrawableTint(getColor(textResColor))

                addDrawableLTRB(
                    drawableL = drawableL,
                    drawableT = drawableT,
                    drawableR = drawableR,
                    drawableB = drawableB,
                    padding = padding
                )
            }

            val toast = Toast(application)
            if (isTopAnchor) {
                toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
            } else {
                toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
            }
            toast.duration = length
            toast.view = layout
            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showShortInformation(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.black
            backgroundResColor = R.color.whiteSmoke
        } else {
            textResColor = R.color.white
            backgroundResColor = R.color.dark900
        }
        show(
            msg = msg,
            length = Toast.LENGTH_SHORT,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showShortWarning(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.white
            backgroundResColor = R.color.darkOrange
        } else {
            textResColor = R.color.black
            backgroundResColor = R.color.yellow
        }
        show(
            msg = msg,
            length = Toast.LENGTH_SHORT,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showShortError(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.white
            backgroundResColor = R.color.red
        } else {
            textResColor = R.color.black
            backgroundResColor = R.color.red
        }
        show(
            msg = msg,
            length = Toast.LENGTH_SHORT,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showLongInformation(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.white
            backgroundResColor = R.color.dark900
        } else {
            textResColor = R.color.black
            backgroundResColor = R.color.whiteSmoke
        }
        show(
            msg = msg,
            length = Toast.LENGTH_LONG,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showLongWarning(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.white
            backgroundResColor = R.color.darkOrange
        } else {
            textResColor = R.color.black
            backgroundResColor = R.color.yellow
        }
        show(
            msg = msg,
            length = Toast.LENGTH_LONG,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showLongError(
        msg: String?,
        isTopAnchor: Boolean = true,
        drawableL: Int? = null,
        drawableT: Int? = null,
        drawableR: Int? = null,
        drawableB: Int? = null,
        padding: Int = 32,
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (application.isDarkTheme()) {
            textResColor = R.color.white
            backgroundResColor = R.color.red
        } else {
            textResColor = R.color.black
            backgroundResColor = R.color.red
        }
        show(
            msg = msg,
            length = Toast.LENGTH_LONG,
            backgroundResColor = backgroundResColor,
            textResColor = textResColor,
            isTopAnchor = isTopAnchor,
            drawableL = drawableL,
            drawableT = drawableT,
            drawableR = drawableR,
            drawableB = drawableB,
            padding = padding,
        )
    }

    fun showShortDebug(msg: String?) {
        if (isDebugMode()) {
            showShortInformation(msg)
        }
    }

    fun showLongDebug(msg: String?) {
        if (isDebugMode()) {
            showLongInformation(msg)
        }
    }
}
