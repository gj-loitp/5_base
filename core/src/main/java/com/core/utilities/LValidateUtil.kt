package com.core.utilities

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import java.util.*
import java.util.regex.Pattern

object LValidateUtil {
    private val TAG = javaClass.simpleName

    val MSG_0 = "pw_must_length_more_8_character"
    val MSG_1 = "pw_must_contain_number"
    val MSG_2 = "pw_must_have_lower_case"
    val MSG_3 = "pw_must_have_upper_case"
    val MSG_4 = "pw_must_have_special_character"

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPassword(context: Context, pw: String): String? {
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
}
