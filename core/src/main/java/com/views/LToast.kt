package com.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.R
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
import java.util.*

object LToast {
    private val logTag = LToast::class.java.simpleName

    private val toastList = ArrayList<Toast>()

    @JvmStatic
    fun show(s: String, isTopAnchor: Boolean = true) {
        show(msg = s, length = 0, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    fun show(resource: Int, isTopAnchor: Boolean = true) {
        show(resource = resource, length = 0, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(msg: String?, isTopAnchor: Boolean = true) {
        show(msg = msg, length = Toast.LENGTH_SHORT, backgroundRes = R.drawable.l_bkg_toast, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(msg: String?, isTopAnchor: Boolean = true) {
        show(msg = msg, length = Toast.LENGTH_LONG, backgroundRes = R.drawable.l_bkg_toast, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(msg: String?, backgroundRes: Int, isTopAnchor: Boolean = true) {
        show(msg = msg, length = Toast.LENGTH_SHORT, backgroundRes = backgroundRes, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(msgRes: Int, backgroundRes: Int, isTopAnchor: Boolean = true) {
        show(msg = LAppResource.getString(msgRes), length = Toast.LENGTH_SHORT, backgroundRes = backgroundRes, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(msg: String?, backgroundRes: Int, isTopAnchor: Boolean = true) {
        show(msg = msg, length = Toast.LENGTH_LONG, backgroundRes = backgroundRes, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(msgRes: Int, backgroundRes: Int, isTopAnchor: Boolean = true) {
        show(msg = LAppResource.getString(msgRes), length = Toast.LENGTH_LONG, backgroundRes = backgroundRes, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun show(resource: Int, length: Int, isTopAnchor: Boolean = true) {
        show(msg = LAppResource.application.resources.getString(resource), length = length, backgroundRes = R.drawable.l_bkg_toast, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun show(resource: Int, length: Int, backgroundRes: Int, isTopAnchor: Boolean = true) {
        show(msg = LAppResource.application.resources.getString(resource), length = length, backgroundRes = backgroundRes, isTopAnchor = isTopAnchor)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    @JvmOverloads
    fun show(msg: String?, length: Int, backgroundRes: Int = R.drawable.l_bkg_toast, isTopAnchor: Boolean = true) {
        if (msg.isNullOrEmpty()) {
            return
        }
        clear()
        try {
            val inf = LAppResource.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inf.inflate(R.layout.l_toast, null)
            val textView = layout.findViewById<View>(R.id.tv_loading) as TextView
            textView.text = msg
            textView.setBackgroundResource(backgroundRes)
            LUIUtil.setTextShadow(textView)
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
    }
}
