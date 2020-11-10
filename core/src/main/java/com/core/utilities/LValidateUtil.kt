package com.core.utilities

import android.text.TextUtils
import android.util.Patterns
import com.BuildConfig
import com.R
import java.util.*
import java.util.regex.Pattern

class LValidateUtil {
    companion object {
        private val logTag = "LValidateUtil"

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
            //check contain number
            val regexNumber = "(.)*(\\d)(.)*"
            val patternNumber = Pattern.compile(regexNumber)
            val containsNumber = patternNumber.matcher(pw).matches()
            if (!containsNumber) {
                return MSG_1
            }
            //check lower case
            val containsLower = pw != pw.toUpperCase(Locale.getDefault())
            if (!containsLower) {
                return MSG_2
            }
            //check upper case
            val containsUpper = pw != pw.toLowerCase(Locale.getDefault())
            if (!containsUpper) {
                return MSG_3
            }
            //check specical character
            val regexSpecial = ("[^!@#$%&*()_+=|<>?{}\\[\\]~-]")
            val patternSpecial = Pattern.compile(regexSpecial)
            val containsSpecial = patternSpecial.matcher(pw).find()
            if (!containsSpecial) {
                return MSG_4
            }
            //null is valid password
            return null
        }

        fun isValidCoreGallery(): Boolean  {
            val value = LSharedPrefsUtil.instance.getString(LSharedPrefsUtil.KEY_CORE_GALLERY)
            if (value == LAppResource.getString(R.string.loitp_core_gallery)) {
                return true
            }
            throw IllegalArgumentException("You have no permission to do this")
        }
    }
}
