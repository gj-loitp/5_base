package com.loitpcore.core.utilities

import android.text.TextUtils
import android.util.Patterns
import com.loitpcore.utils.util.AppUtils
import java.util.* // ktlint-disable no-wildcard-imports
import java.util.regex.Pattern

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LValidateUtil {
    companion object {

        const val MSG_0 = "pw_must_length_more_8_character"
        const val MSG_1 = "pw_must_contain_number"
        const val MSG_2 = "pw_must_have_lower_case"
        const val MSG_3 = "pw_must_have_upper_case"
        const val MSG_4 = "pw_must_have_special_character"

        fun isValidEmail(target: CharSequence): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun isValidPassword(pw: String): String? {
            if (pw.length < 8) {
                return MSG_0
            }
            // check contain number
            val regexNumber = "(.)*(\\d)(.)*"
            val patternNumber = Pattern.compile(regexNumber)
            val containsNumber = patternNumber.matcher(pw).matches()
            if (!containsNumber) {
                return MSG_1
            }
            // check lower case
            val containsLower = pw != pw.uppercase(Locale.getDefault())
            if (!containsLower) {
                return MSG_2
            }
            // check upper case
            val containsUpper = pw != pw.lowercase(Locale.getDefault())
            if (!containsUpper) {
                return MSG_3
            }
            // check special character
            val regexSpecial = ("[^!@#$%&*()_+=|<>?{}\\[\\]~-]")
            val patternSpecial = Pattern.compile(regexSpecial)
            val containsSpecial = patternSpecial.matcher(pw).find()
            if (!containsSpecial) {
                return MSG_4
            }
            // null is valid password
            return null
        }

        private val listPkg = listOf(
            "loitp.basemaster", // admob ban
            "loitp93.basemaster.demo", // admob ban
            "loitp93.anhseyeuemtucainhindautien", // admob ban
            "com.mup.comic", // admob ban
            "loitp93.game.findnumber", // admob ban
            "com.loitp.igallery", // admob ban
            "com.loitp.haivl", // admob ban
            "com.loitp.biker", // admob ban
            "com.loitp.icomic", // admob ban
            "loitp93.truyenvn.cute.girl", // admob ban
            "loitp93.rss.vnexpress", // admob ban
            "loitp93.basemaster",
            "loitp.khotruyenvuive",
            "com.roygroup.base",
            "com.roygroup.vnexpress",
            "com.roygroup.base.demo",
            "com.roy93group.todo",
            "com.roy93group.vocabulary",
            "com.roy93group.tictactoe"
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
    }
}
