package com.loitp.func.mee.tokens

import com.loitp.func.mee.parser.IRule

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/
sealed class Token(
    var value: String,
    val priority: Int = 0,
) : IRule


operator fun Token.plus(token: Token): Token {
    if (this is NumberType && token is NumberType) {
        return if (value.contains(".") or token.value.contains("."))
            NumberType.FloatType(
                "${token.value.toDouble() + value.toDouble()}"
            )
        else NumberType.IntType(
            "${token.value.toInt() + value.toInt()}"
        )
    }
    error("Plus operation not allowed in tokens '$this' and '$token' ")
}

operator fun Token.minus(token: Token): Token {
    if (this is NumberType && token is NumberType) {
        return if (value.contains(".") or token.value.contains("."))
            NumberType.FloatType(
                number = "${token.value.toDouble() - value.toDouble()}"
            )
        else NumberType.IntType(
            number = "${token.value.toInt() - value.toInt()}"
        )
    }
    error("minus operation not allowed in tokens '$this' and '$token' ")
}

operator fun Token.times(token: Token): Token {
    if (this is NumberType && token is NumberType) {
        return if (value.contains(".") or token.value.contains("."))
            NumberType.FloatType(
                number = "${token.value.toDouble() * value.toDouble()}"
            )
        else NumberType.IntType(
            number = "${token.value.toInt() * value.toInt()}"
        )
    }
    error("minus operation not allowed in tokens '$this' and '$token' ")
}

operator fun Token.div(token: Token): Token {
    if (this is NumberType && token is NumberType) {
        return NumberType.FloatType(
            number = "${token.value.toFloat() / value.toFloat()}"
        )
    }
    error("minus operation not allowed in tokens '$this' and '$token' ")
}
