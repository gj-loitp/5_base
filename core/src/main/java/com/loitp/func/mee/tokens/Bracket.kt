package com.loitp.func.mee.tokens

import isBracket
import java.util.*

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/

sealed class Bracket(bracket: String) : Token(bracket) {
    object LBracket : Bracket("(") {
        override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
            stack.push(this)
        }

        override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
            TODO("Not yet implemented")
        }
    }

    object RBracket : Bracket(")") {

        override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
            if (stack.isEmpty())
                error("Brackets mismatch '${value}'")
            while (stack.peek() != LBracket) {
                queue.add(stack.pop())
                if (stack.isEmpty())
                    error("Brackets mismatch '${value}'")
            }
            if (stack.peek() != LBracket) {
                error("Brackets mismatch '${value}'")
            }
            stack.pop() // remove the opened bracket
        }

        override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
            TODO("Not yet implemented")
        }
    }
}

fun String.toBracket(): Bracket {
    if (!this.isBracket())
        error("'$this' is not a Bracket")
    return if (this == ")")
        Bracket.RBracket
    else Bracket.LBracket
}