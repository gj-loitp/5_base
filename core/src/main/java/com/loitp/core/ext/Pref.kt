package com.loitp.core.ext

import com.loitp.core.common.Constants
import com.loitp.core.utilities.LSharedPrefsUtil

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun isDarkTheme(): Boolean {
//            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    return LSharedPrefsUtil.instance.getBoolean(Constants.IS_DARK_THEME, false)
}

fun setDarkTheme(isDarkTheme: Boolean) {
    if (isDarkTheme) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, true)
    } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, false)
    }
}
