package com.loitp.core.ext

import android.text.TextUtils
import android.util.Patterns
import com.loitp.core.utils.AppUtils
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Suppress("unused")
const val MSG_0 = "pw_must_length_more_8_character"

@Suppress("unused")
const val MSG_1 = "pw_must_contain_number"

@Suppress("unused")
const val MSG_2 = "pw_must_have_lower_case"

@Suppress("unused")
const val MSG_3 = "pw_must_have_upper_case"

@Suppress("unused")
const val MSG_4 = "pw_must_have_special_character"

fun CharSequence.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): String? {
    if (this.length < 8) {
        return MSG_0
    }
    // check contain number
    val regexNumber = "(.)*(\\d)(.)*"
    val patternNumber = Pattern.compile(regexNumber)
    val containsNumber = patternNumber.matcher(this).matches()
    if (!containsNumber) {
        return MSG_1
    }
    // check lower case
    val containsLower = this != this.uppercase(Locale.getDefault())
    if (!containsLower) {
        return MSG_2
    }
    // check upper case
    val containsUpper = this != this.lowercase(Locale.getDefault())
    if (!containsUpper) {
        return MSG_3
    }
    // check special character
    val regexSpecial = ("[^!@#$%&*()_+=|<>?{}\\[\\]~-]")
    val patternSpecial = Pattern.compile(regexSpecial)
    val containsSpecial = patternSpecial.matcher(this).find()
    if (!containsSpecial) {
        return MSG_4
    }
    // null is valid password
    return null
}

private val listPkg = listOf(
    "loitp93.basemaster",
    "loitp.khotruyenvuive",
    "com.roygroup.base",
    "com.roygroup.vnexpress",
    "com.roygroup.base.demo",
    "com.roy93group.todo",
    "com.roy93group.vocabulary",
    "com.roy93group.tictactoe",
    "com.roy93group.pianokiss",
    "com.roy93group.asyetcndt",
    "com.roy93group.reversi",
    "com.roy93group.cintalauncher",
    "com.bizman.bizman_dikauri",//for thanos bizman
    "com.songnob.adsplayer",//for thanos table tap
    "com.songnob.adsplayer2",//for thanos order top
)

fun isValidPackageName(): Boolean {
    val isValid = listPkg.any {
        it == AppUtils.appPackageName
    }
    if (isValid) {
        return true
    }
    throw IllegalArgumentException("You have no permission to do this")
}
