package com.loitp.core.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
import com.loitp.R
import com.simmorsal.recolor_project.OnReColorFinish
import com.simmorsal.recolor_project.ReColor
import java.util.*

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
var lastOffset = 0.0f
var isUp = false
val listColorLight = intArrayOf(
    R.color.lightBlue,
    R.color.lightCoral,
    R.color.lightCyan,
    R.color.lightGoldenrodYellow,
    R.color.lightGreen,
    R.color.lightGrey,
    R.color.lightPink,
    R.color.lightSalmon,
    R.color.lightSeaGreen,
    R.color.lightSlateGray,
    R.color.lightSteelBlue,
    R.color.lightYellow,
    R.color.lightSkyBlue,
    R.color.ripple_material_dark,
    R.color.grey,
    R.color.scratchEndGradient,
    R.color.lemonChiffon,
    R.color.bisque,
    R.color.hotPink,
    R.color.salmon,
    R.color.wheat,
    R.color.honeydew,
    R.color.paleGoldenrod,
    R.color.violet,
    R.color.lavender,
    R.color.gainsboro,
    R.color.orchid,
    R.color.tan,
    R.color.indianRed,
    R.color.darkKhaki,
    R.color.mediumOrchid,
    R.color.powderBlue,
    R.color.greenYellow,
    R.color.paleGreen,
    R.color.mediumPurple,
    R.color.darkSeaGreen,
    R.color.skyBlue,
    R.color.slateGray,
    R.color.oliveDrab,
    R.color.cornflowerBlue,
    R.color.mediumTurquoise,
    R.color.mediumSeaGreen,
    R.color.dodgerBlue,
    R.color.aqua,
    R.color.springGreen,
    R.color.periwinkle,
    R.color.pink,
    R.color.default_selected_day_background_start_color,
    R.color.default_selection_bar_month_title_text_color
)

//fun getRandomColorLight(): Int {
//    val random = Random()
//    val index = random.nextInt(listColorLight.size)
//    return listColorLight[index]
//}

val randomColorLight: Int
    get() {
        val mRandom = Random(System.currentTimeMillis())
        val baseColor = Color.WHITE

        val baseRed = Color.red(baseColor)
        val baseGreen = Color.green(baseColor)
        val baseBlue = Color.blue(baseColor)

        val red = (baseRed + mRandom.nextInt(256)) / 2
        val green = (baseGreen + mRandom.nextInt(256)) / 2
        val blue = (baseBlue + mRandom.nextInt(256)) / 2

        return Color.rgb(red, green, blue)
    }

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun Int.setAlphaComponent(
    alpha: Int = 50
): Int {
    return ColorUtils.setAlphaComponent(/* color = */ this, /* alpha = */ alpha)
}

fun Context.recolorStatusBarPulse(
    pulseColor: Int,
    pulseSpeed: Int = 300,
    pulseCount: Int = 2,
) {
    val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
    ReColor(this).pulseStatusBar(
        pulseColorString,
        pulseSpeed,
        pulseCount,
    )
}

fun Context.recolorStatusBar(
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
    ReColor(this).setStatusBarColor(
        /* startingColor = */ hexColorStart,
        /* endingColor = */hexColorEnd,
        /* duration = */duration
    ).setOnReColorFinish(onReColorFinish)
}

fun Context.recolorNavigationBarPulse(
    pulseColor: Int,
    pulseSpeed: Int = 300,
    pulseCount: Int = 2,
) {
    val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
    ReColor(this).pulseNavigationBar(
        pulseColorString,
        pulseSpeed,
        pulseCount,
    )
}

fun Context.recolorNavigationBar(
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
    ReColor(this).setNavigationBarColor(
        /* startingColor = */ hexColorStart,
        /* endingColor = */hexColorEnd,
        /* duration = */duration
    ).setOnReColorFinish(onReColorFinish)
}

fun View.recolor(
    startColor: Int,
    endColor: Int,
    duration: Int = 300,
    onReColorFinish: OnReColorFinish? = null
) {
    val hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
    val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
    when (this) {
        is ImageButton -> {
            ReColor(this.context).setImageButtonColorFilter(
                this,
                /* startingColor = */hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }
        is ImageView -> {
            ReColor(this.context).setImageViewColorFilter(
                /* imageView = */ this,
                /* startingColor = */ hexColorStart,
                /* endingColor = */ hexColorEnd,
                /* duration = */ duration
            ).setOnReColorFinish(onReColorFinish)
        }
        is TextView -> {
            ReColor(this.context).setTextViewColor(
                /* textView = */ this,
                /* startingColor = */hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }
        is CardView -> {
            ReColor(this.context).setCardViewColor(
                this,
                /* startingColor = */hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }
        else -> {
            ReColor(this.context).setViewBackgroundColor(
                /* view = */ this,
                /* startingColor = */ hexColorStart,
                /* endingColor = */ hexColorEnd,
                /* duration = */ duration
            ).setOnReColorFinish(onReColorFinish)
        }
    }

}

val colors: IntArray
    get() = intArrayOf(
        Color.parseColor("#1BFFFF"),
        Color.parseColor("#2E3192"),
        Color.parseColor("#ED1E79"),
        Color.parseColor("#009E00"),
        Color.parseColor("#FBB03B"),
        Color.parseColor("#D4145A"),
        Color.parseColor("#3AA17E"),
        Color.parseColor("#00537E")
    )

val texts: Array<String>
    get() = arrayOf(
        "Relax, its only ONES and ZEROS !",
        "Hardware: The parts of a computer system that can be kicked.",
        "Computer dating is fine, if you're a computer.",
        "Better to be a geek than an idiot.",
        "If you don't want to be replaced by a computer, don't act like one.",
        "I'm not anti-social; I'm just not user friendly",
        "Those who can't write programs, write help files.",
        "The more I C, the less I see.  "
    )
