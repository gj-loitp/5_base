package com.loitp.func.mee.tokens

import isFloat
import isInt
import java.util.*

sealed class NumberType(
    value: String,
) : Token(value) {
    data class FloatType(var number: String) : NumberType(number)
    data class IntType(private val number: String) : NumberType(number)

    override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        queue.add(this)
    }

    override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        TODO("Not yet implemented")
    }
}

fun String.toNumberType(): Token {
    return if (this.isFloat())
        NumberType.FloatType(this)
    else if (this.isInt())
        NumberType.IntType(this)
    else error("not numner")

}
