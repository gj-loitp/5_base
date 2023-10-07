package com.loitp.func.mee.tokens

import java.util.Queue
import java.util.Stack

/**
 * Author : Abdelmajid ID ALI
 * On : 17/08/2021
 * Email :  abdelmajid.idali@gmail.com
 **/
sealed class Operation(value: String, priority: Int) : Token(value, priority) {
    object Subtraction : Operation("-", 1)
    object Addition : Operation("+", 1)
    object Division : Operation("/", 2)
    object Multiplication : Operation("*", 2)

    fun evaluate(first: Token, second: Token): Token {
        return when (this) {
            Addition -> first + second
            Subtraction -> first - second
            Division -> first / second
            Multiplication -> first * second
        }
    }

    override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {

        if (stack.isEmpty()) {
            stack.push(this)
        } else {
            val top = stack.peek()
//            if (top !is Operation) {
//                stack.push(token)
//                return
//            }
            if (top.priority < this.priority) {
                stack.push(this)
            } else if (top.priority == this.priority) {
                queue.add(stack.pop())
                stack.push(this)
            } else {
                while (stack.isNotEmpty() && stack.peek().priority >= this.priority) {
                    queue.add(stack.pop())
                }
                stack.push(this)
            }
        }

    }

    override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        TODO("Not yet implemented")
    }
}

fun String.toOperation(): Operation {
    return when (this) {
        "+" -> Operation.Addition
        "-" -> Operation.Subtraction
        "*", "×" -> Operation.Multiplication
        "/", "÷" -> Operation.Division
        else -> error("lexer:String.toOperation, $this is not an operation")
    }
}

operator fun Operation.compareTo(operation: Operation): Int {
    return priority.compareTo(operation.priority)
}
