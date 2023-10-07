package com.loitp.func.mee.parser

import com.loitp.func.mee.lexer.Lexer
import com.loitp.func.mee.tokens.Token
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/
class Parser(private val lexer: Lexer) {

    /**
     * Convert infix tokens to postfix tokens
     */
    fun parseTokens(): Queue<Token> {
        val tokens: Queue<Token> = LinkedList(lexer.readTokens())
        val stack = Stack<Token>()
        val queue: Queue<Token> = LinkedList()
        while (tokens.isNotEmpty()) {
            tokens.poll()?.onParse(tokens, queue, stack)
        }
        while (stack.isNotEmpty()) {
            queue.add(stack.pop())
        }
        return queue
    }


    private fun parserError(message: String): Nothing = error("[Parser]: error: $message")
}
