package com.loitp.views.toast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import com.loitp.BuildConfig
import com.loitp.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LToast {

    private val toastList = ArrayList<Toast>()

    @JvmStatic
    @SuppressLint("InflateParams")
    @JvmOverloads
    fun show(
        msg: String?,
        length: Int = Toast.LENGTH_SHORT,
        backgroundResColor: Int = R.color.red,
        textResColor: Int = R.color.white,
        isTopAnchor: Boolean = true
    ) {
        if (msg.isNullOrEmpty()) {
            return
        }
        clear()
        try {
            val inf =
                LAppResource.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inf.inflate(R.layout.l_v_toast, null)
            val textView = layout.findViewById<AppCompatTextView>(R.id.tvLoading)
            textView.text = msg
            textView.setBackgroundColor(LAppResource.getColor(backgroundResColor))
            textView.setTextColor(LAppResource.getColor(textResColor))
            val toast = Toast(LAppResource.application)
            if (isTopAnchor) {
                toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
            } else {
                toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
            }
            toast.duration = length
            toast.view = layout
            toast.show()
            toastList.add(toast)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clear() {
        for (i in toastList.indices) {
            toastList[i].cancel()
        }
        toastList.clear()
    }

    fun showShortInformation(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showShortWarning(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showShortError(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showLongInformation(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showLongWarning(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showLongError(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        val textResColor: Int
        val backgroundResColor: Int
        if (LUIUtil.isDarkTheme()) {
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
            isTopAnchor = isTopAnchor
        )
    }

    fun showShortDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            showShortDebug(msg)
        }
    }

    fun showLongDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            showLongInformation(msg)
        }
    }
}
