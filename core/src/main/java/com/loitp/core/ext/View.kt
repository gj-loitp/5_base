package com.loitp.core.ext

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback

val View.horizontalPadding: Int get() = this.paddingStart + this.paddingEnd
val View.verticalPadding: Int get() = this.paddingTop + this.paddingBottom

inline fun <reified T : View> T.doOnNextNonNullSizeLayout(crossinline action: (view: T) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            if (view.width == 0 || view.height == 0) return
            view.removeOnLayoutChangeListener(this)
            action(view as T)
        }
    })
}

inline fun <reified T : View> T.doOnNonNullSizeLayout(crossinline action: (view: T) -> Unit) {
    if (ViewCompat.isLaidOut(this) && !isLayoutRequested && this.width > 0 && this.height > 0) {
        action(this)
    } else {
        doOnNextNonNullSizeLayout { action(it) }
    }
}

fun View.duplicateViewSizeContinuously(
    view: View,
    duplicateWidth: Boolean = true,
    duplicateHeight: Boolean = true,
    overrideMinimumWidth: Boolean = true,
    overrideMinimumHeight: Boolean = true,
    ignoreNullOrInvisibleValues: Boolean = true,
    fallbackLayoutParamWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    fallbackLayoutParamHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    transformWidth: Int.() -> Int = { this },
    transformHeight: Int.() -> Int = { this }
) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        val shouldFallbackToWidth =
            ignoreNullOrInvisibleValues && (!view.isVisible || view.width == 0)
        val shouldFallbackToHeight =
            ignoreNullOrInvisibleValues && (!view.isVisible || view.height == 0)
        updateLayoutParams<ViewGroup.LayoutParams> {
            if (duplicateWidth) width = when {
                shouldFallbackToWidth -> fallbackLayoutParamWidth
                overrideMinimumWidth -> transformWidth(view.width)
                else -> transformWidth(view.width.coerceAtLeast(minimumWidth))
            }
            if (duplicateHeight) height = when {
                shouldFallbackToHeight -> fallbackLayoutParamHeight
                overrideMinimumHeight -> transformHeight(view.height)
                else -> transformHeight(view.height.coerceAtLeast(minimumHeight))
            }
        }
    }
}

@Suppress("unused")
//doi mau cua lottie
fun LottieAnimationView.changeLayersColor(
    color: Int
) {
    val filter = SimpleColorFilter(color)
    val keyPath = KeyPath("**")
    val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)

    addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
}

fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val width = if (bounds.isEmpty) intrinsicWidth else bounds.width()
    val height = if (bounds.isEmpty) intrinsicHeight else bounds.height()

    return Bitmap.createBitmap(width.nonZero(), height.nonZero(), Bitmap.Config.ARGB_8888).also {
        val canvas = Canvas(it)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
    }
}

private fun Int.nonZero() = if (this <= 0) 1 else this

//get mau background current cua view
fun View.getBackgroundColor() = (background as? ColorDrawable?)?.color ?: Color.TRANSPARENT

fun View.setBackgroundTintList(color: Int) {
    this.backgroundTintList = ColorStateList.valueOf(color)
}

//This snippet hides the system bars.
fun View.hideSystemUI() {
    // Set the IMMERSIVE flag.
    // Set the content to appear under the system bars so that the content
    // doesn't resize when the system bars hide and show.
    this.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
            )
}

// This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
fun View.showSystemUI() {
    this.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
}
