package com.loitp.core.utilities

import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.snackbar.Snackbar
import com.loitp.R
import com.loitp.core.ext.*
import com.loitp.func.wallpo.Wallpo
import com.simmorsal.recolor_project.OnReColorFinish
import com.simmorsal.recolor_project.ReColor
import com.skydoves.elasticviews.elasticAnimation
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LUIUtil {
    companion object {

        fun setSafeOnClickListenerElastic(
            view: View? = null,
            scaleX: Float = 0.8f,
            scaleY: Float = 0.8f,
            duration: Int = 50,
            runnable: Runnable? = null
        ) {
            view?.let { v ->
                v.setSafeOnClickListener {
                    val anim = v.elasticAnimation(
                        scaleX = scaleX, scaleY = scaleY, duration = duration
                    ) {
                        runnable?.run()
                    }
                    anim.doAction()
                }
            }
        }

        fun Snackbar.withBackground(resId: Int): Snackbar {
//            this.view.setBackgroundColor(colorInt)
            this.view.setBackgroundResource(resId)
            return this
        }

        fun Snackbar.allowInfiniteLines(): Snackbar {
            return apply {
                (view.findViewById<View?>(R.id.snackbar_text) as? TextView?)?.let {
                    it.isSingleLine = false
                    it.setTextColor(Color.WHITE)
                    it.setTextSizePx(
                        size = getDimenValue(R.dimen.txt_medium).toFloat()
                    )
                    it.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }

        fun setDrawableTintColor(
            textView: TextView, color: Int
        ) {
            for (drawable in textView.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            }
        }

        @Suppress("unused")
        fun setTypeface(
            textView: TextView, pathFontAsset: String
        ) {
            val type = Typeface.createFromAsset(textView.context.assets, pathFontAsset)
            textView.typeface = type
        }

        fun setWallpaperAndLockScreen(
            context: Context,
            imageView: ImageView,
            isSetWallpaper: Boolean = true,
            isSetLockScreen: Boolean = true,
        ) {
            if (isSetWallpaper) {
                Wallpo.setMainScreenWallpaper(
                    context = context,
                    imageView = imageView,
                )
            }
            if (isSetLockScreen) {
                Wallpo.setLockScreenWallpaper(
                    context = context,
                    imageView = imageView,
                )
            }
        }

        fun setWallpaperAndLockScreen(
            context: Context,
            color: Int,
            isSetWallpaper: Boolean = true,
            isSetLockScreen: Boolean = true,
        ) {
            if (isSetWallpaper) {
                Wallpo.setMainScreenWallpaper(
                    context = context,
                    color = color,
                )
            }
            if (isSetLockScreen) {
                Wallpo.setLockScreenWallpaper(
                    context = context,
                    color = color,
                )
            }
        }

        fun setAlphaComponent(
            color: Int, alpha: Int = 50
        ): Int {
            return ColorUtils.setAlphaComponent(/* color = */ color, /* alpha = */ alpha)
        }

        fun setTextUnderline(tv: TextView) {
            tv.paintFlags = tv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }

        fun setCornerCardView(
            cardView: MaterialCardView,
            radiusTL: Float,
            radiusTR: Float,
            radiusBL: Float,
            radiusBR: Float,
        ) {
            cardView.shapeAppearanceModel = cardView.shapeAppearanceModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radiusTL)
                .setTopRightCorner(CornerFamily.ROUNDED, radiusTR)
                .setBottomRightCorner(CornerFamily.ROUNDED, radiusBL)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radiusBR).build()
        }

        fun recolorStatusBarPulse(
            context: Context,
            pulseColor: Int,
            pulseSpeed: Int = 300,
            pulseCount: Int = 2,
        ) {
            val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
            ReColor(context).pulseStatusBar(
                pulseColorString,
                pulseSpeed,
                pulseCount,
            )
        }

        fun recolorStatusBar(
            context: Context,
            startColor: Int? = null,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            // if starting color is null, color will be automatically retrieved from status bar
            // same is true for navigation bar
            var hexColorStart: String? = null
            if (startColor == null) {
                //do nothing
            } else {
                hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            }
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
            ReColor(context).setStatusBarColor(
                /* startingColor = */ hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }

        fun recolorNavigationBarPulse(
            context: Context,
            pulseColor: Int,
            pulseSpeed: Int = 300,
            pulseCount: Int = 2,
        ) {
            val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
            ReColor(context).pulseNavigationBar(
                pulseColorString,
                pulseSpeed,
                pulseCount,
            )
        }

        fun recolorNavigationBar(
            context: Context,
            startColor: Int? = null,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            // if starting color is null, color will be automatically retrieved from status bar
            // same is true for navigation bar
            var hexColorStart: String? = null
            if (startColor == null) {
                //do nothing
            } else {
                hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            }
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
            ReColor(context).setNavigationBarColor(
                /* startingColor = */ hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }

        fun recolor(
            view: View,
            startColor: Int,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            val hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
//            LLog.e("loitpp", "hexColorStart $hexColorStart")
//            LLog.e("loitpp", "hexColorEnd $hexColorEnd")
            when (view) {
                is ImageButton -> {
                    ReColor(view.context).setImageButtonColorFilter(
                        view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is ImageView -> {
                    ReColor(view.context).setImageViewColorFilter(
                        /* imageView = */ view,
                        /* startingColor = */ hexColorStart,
                        /* endingColor = */ hexColorEnd,
                        /* duration = */ duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is TextView -> {
                    ReColor(view.context).setTextViewColor(
                        /* textView = */ view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is CardView -> {
                    ReColor(view.context).setCardViewColor(
                        view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                else -> {
                    ReColor(view.context).setViewBackgroundColor(
                        /* view = */ view,
                        /* startingColor = */ hexColorStart,
                        /* endingColor = */ hexColorEnd,
                        /* duration = */ duration
                    ).setOnReColorFinish(onReColorFinish)
                }
            }

        }
    }
}
