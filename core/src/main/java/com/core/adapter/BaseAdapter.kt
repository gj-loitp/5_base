package com.core.adapter

import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.annotation.LogTag
import com.core.utilities.LLog
import com.google.ads.interactivemedia.v3.internal.it
import com.views.LToast
import jp.wasabeef.recyclerview.adapters.*

//https://github.com/wasabeef/recyclerview-animators
/*bookAdapter?.let {
    val scaleAdapter = AlphaInAnimationAdapter(it)
    val scaleAdapter = ScaleInAnimationAdapter(it)
    val scaleAdapter = SlideInBottomAnimationAdapter(it)
    val scaleAdapter = SlideInLeftAnimationAdapter(it)
    val scaleAdapter = SlideInRightAnimationAdapter(it)

    scaleAdapter.setDuration(1000)
    scaleAdapter.setInterpolator(OvershootInterpolator())
    scaleAdapter.setFirstOnly(true)
    rv.adapter = scaleAdapter
}*/
abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var logTag: String? = null

    init {
        logTag = javaClass.getAnnotation(LogTag::class.java)?.value
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
