package com.loitp.core.ext

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun BigDecimal?.convertToPrice(): String {
    if (this == null) {
        return ""
    }
    val symbols = DecimalFormatSymbols(Locale.US)
    val formatter = DecimalFormat("#,###", symbols)

    var priceString = formatter.format(this.roundBigDecimal())
    if (priceString == "-0" || priceString == "-," || priceString == ",") {
        priceString = "0"
    }
    return priceString
}

fun Number?.convertNumberToStringFormat(): String {
    if (this == null) {
        return ""
    }
    val splitValue = this.toString().split(".")
    val firstValue: BigDecimal?
    var secondValue: BigDecimal? = null
    if (splitValue.size > 1) {
        firstValue = splitValue.first().toBigDecimalOrNull()
        secondValue = splitValue.last().toBigDecimalOrNull()
    } else {
        firstValue = splitValue.first().toBigDecimalOrNull()
    }

    return if (secondValue != null) {
        "${firstValue.convertToPrice()}.$secondValue"
    } else {
        firstValue.convertToPrice()
    }
}

fun Number?.convertNumberToString(): String {
    if (this == null) {
        return ""
    }
    val splitValue = this.toString().split(".")
    if (splitValue.lastOrNull()?.toIntOrNull() == 0) {
        return splitValue.firstOrNull() ?: ""
    }
    return this.toString()
}

fun Number?.convertNumberToPercent(): String {
    if (this == null) {
        return ""
    }
    val result = (this.toFloat() * 1000).roundToInt().toFloat() / 1000
    return result.convertNumberToString()
}

fun BigDecimal.roundBigDecimal(
    newScale: Int = 0
): BigDecimal {
    return this.setScale(newScale, BigDecimal.ROUND_HALF_UP)
}
