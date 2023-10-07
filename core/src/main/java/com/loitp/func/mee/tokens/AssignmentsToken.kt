package com.loitp.func.mee.tokens

import java.util.*

/**
 ** Author : Abdelmajid ID ALI
 ** On : 24/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/

object SemicolonToken : Token(";") {
    override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
    }

    override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
    }
}

object AssignmentToken : Token("=") {
    override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
    }

    override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
    }
}