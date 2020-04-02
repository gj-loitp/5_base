package com.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.R
import com.core.utilities.LLog
import java.util.*

object LToast {
    private val TAG = LToast::class.java.simpleName

    private val toastList = ArrayList<Toast>()

    @JvmStatic
    fun show(context: Context, s: String) {
        show(context, s, 0)
    }

    @JvmStatic
    fun show(context: Context, resource: Int) {
        show(context, resource, 0)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(context: Context, msg: String?) {
        show(context, msg, Toast.LENGTH_SHORT, R.drawable.l_bkg_horizontal)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(context: Context, msg: String?) {
        show(context, msg, Toast.LENGTH_LONG, R.drawable.l_bkg_horizontal)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(context: Context, msg: String?, backgroundRes: Int) {
        show(context, msg, Toast.LENGTH_SHORT, backgroundRes)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showShort(context: Context, msgRes: Int, backgroundRes: Int) {
        show(context, context.getString(msgRes), Toast.LENGTH_SHORT, backgroundRes)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(context: Context, msg: String?, backgroundRes: Int) {
        show(context, msg, Toast.LENGTH_LONG, backgroundRes)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun showLong(context: Context, msgRes: Int, backgroundRes: Int) {
        show(context, context.getString(msgRes), Toast.LENGTH_LONG, backgroundRes)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun show(context: Context, resource: Int, length: Int) {
        show(context, context.resources.getString(resource), length, R.drawable.l_bkg_horizontal)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    fun show(context: Context, resource: Int, length: Int, backgroundRes: Int) {
        show(context, context.resources.getString(resource), length, backgroundRes)
    }

    @JvmStatic
    @SuppressLint("InflateParams")
    @JvmOverloads
    fun show(context: Context, msg: String?, length: Int, backgroundRes: Int = R.drawable.l_bkg_horizontal) {
        if (msg == null) {
            return
        }
        clear()
        try {
            val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inf.inflate(R.layout.l_toast, null)
            val textView = layout.findViewById<View>(R.id.tv_loading) as TextView
            textView.text = msg
            textView.setBackgroundResource(backgroundRes)
            val toast = Toast(context)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
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
