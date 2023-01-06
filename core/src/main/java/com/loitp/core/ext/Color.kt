package com.loitp.core.ext

import android.graphics.Color
import com.loitp.R
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

fun getRandomColorLight(): Int {
    val random = Random()
    val index = random.nextInt(listColorLight.size)
    return listColorLight[index]
}

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}
