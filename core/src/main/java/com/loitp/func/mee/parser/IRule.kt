package com.loitp.func.mee.parser

import com.loitp.func.mee.tokens.Token
import java.util.*

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/
interface IRule {
    fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>)
    fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>)
}
