package com.core.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
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

    protected fun showShortInformation(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortInformation(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortWarning(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortWarning(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortError(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showShortError(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongInformation(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongInformation(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongWarning(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongWarning(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showLongError(msg: String?, isTopAnchor: Boolean = true) {
        LToast.showLongError(msg = msg, isTopAnchor = isTopAnchor)
    }

    protected fun showShortDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            LToast.showShortDebug(msg)
        }
    }

    protected fun showLongDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            LToast.showLongInformation(msg)
        }
    }
}
