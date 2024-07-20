/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/

/**
 *Check whatever string is a math operations or not
 * operations : [+ * / -]
 * @return true if is operation
 */
fun String.isOperation(): Boolean {
    return this.matches("[+*/×÷-]".toRegex())
}

/**
 * Check whatever string is a number or not
 * @return true if is number
 */
fun String.isNumber(): Boolean {
    return this.matches("(\\d+(\\.\\d+)?)".toRegex())
}

fun String.isFloat(): Boolean {
    return this.count { it == '.' } == 1 && isNumber()
}

fun String.isInt(): Boolean {
    return !this.contains(".") && isNumber()
}

fun String.isBracket(): Boolean {
    return (this == "(") or (this == ")")
}
