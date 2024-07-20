package com.loitp.func.mee.lexer

import isBracket
import isNumber
import isOperation
import com.loitp.func.mee.source.ISource
import com.loitp.func.mee.tokens.AssignmentToken
import com.loitp.func.mee.tokens.SemicolonToken
import com.loitp.func.mee.tokens.Token
import com.loitp.func.mee.tokens.isMathFun
import com.loitp.func.mee.tokens.isVariableName
import com.loitp.func.mee.tokens.toBracket
import com.loitp.func.mee.tokens.toMathFun
import com.loitp.func.mee.tokens.toNumberType
import com.loitp.func.mee.tokens.toOperation
import com.loitp.func.mee.tokens.toVariableToken

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/

private val TOKENS_REGEX: String
    get() = """
        ([()])|(\d+(\.\d+)?)|([+*×÷/-])|log|ln|exp|cos|sin|sqrt|(\^)|([=;])|([a-z,A-Z])
    """.trimIndent()

class Lexer(private val source: ISource) {

    fun readTokens(): List<Token> {
        if (source.getLines().isEmpty())
            return emptyList()

        val lines = source.getText()
        val regex = TOKENS_REGEX.toRegex()

        val nonAllowedTokens = regex.split(lines)
            .filter { it.isNotBlank() && it != "\n" && it != " " }

        if (nonAllowedTokens.isNotEmpty()) {
            lexerError(" Symbols [${nonAllowedTokens.joinToString()}] not allowed")
        }

        val tokens = ArrayList<Token>()

        regex.findAll(lines)
            .filter { it.value.isNotBlank() }
            .forEach {
                val strToken = it.value
                val token: Token = when {
                    strToken.isNumber() -> strToken.toNumberType()
                    strToken.isOperation() -> strToken.toOperation()
                    strToken.isBracket() -> strToken.toBracket()
                    strToken.isMathFun() -> strToken.toMathFun()
                    strToken.isVariableName() -> strToken.toVariableToken()
                    strToken == "=" -> AssignmentToken
                    strToken == ";" -> SemicolonToken
                    else -> {
                        lexerError("keyword '$strToken' not allowed")
                    }
                }
                tokens.add(token)
            }

        return tokens
    }

    private fun lexerError(message: String): Nothing = error("[Lexer] error : $message")
}
