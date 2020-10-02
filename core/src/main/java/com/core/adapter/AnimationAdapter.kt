package com.core.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.common.Constants
import com.core.utilities.LAnimationUtil
import com.core.utilities.LLog
import com.views.LToast

abstract class AnimationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastPosition = -1
    protected var logTag: String? = null

    init {
        logTag = javaClass.getAnnotation(LogTag::class.java)?.value
    }

    fun setAnimation(viewToAnimate: View?, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        viewToAnimate?.let {
            if (position > lastPosition) {
                LAnimationUtil.playAnimRandomDuration(viewToAnimate = it)
                lastPosition = position
            }
        }
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(it, msg)
        }
    }

    protected fun showShort(msg: String?) {
        LToast.showShort(msg = msg, backgroundRes = R.drawable.l_bkg_horizontal)
    }

    protected fun showLong(msg: String?) {
        LToast.showLong(msg = msg, backgroundRes = R.drawable.l_bkg_horizontal)
    }

    protected fun showShortDebug(msg: String?) {
        if (Constants.IS_DEBUG) {
            showShort(msg)
        }
    }

    protected fun showLongDebug(msg: String?) {
        if (Constants.IS_DEBUG) {
            showLong(msg)
        }
    }
}
