package com.loitp.func.mee.tokens

import java.util.*
import kotlin.math.*

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/

sealed class FunctionType(name: String, priority: Int = 6) : Token(value = name, priority) {

    override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        stack.push(this)
    }

    override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        TODO("Not yet implemented")
    }

    abstract fun calculateFunction(stack: Stack<Token>)

    object LogFunction : FunctionType("log") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = log10(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object LnFunction : FunctionType("ln") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = ln(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object ExpFunction : FunctionType("exp") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = exp(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object CosFunction : FunctionType("cos") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = cos(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object SinFunction : FunctionType("sin") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = sin(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object SqrtFunction : FunctionType("sqrt") {
        override fun calculateFunction(stack: Stack<Token>) {
            val result = sqrt(stack.pop().value.toDouble())
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }

    object PowFunction : FunctionType("^") {
        override fun calculateFunction(stack: Stack<Token>) {
            val exponent = stack.pop().value.toDouble()
            val base = stack.pop().value.toDouble()
            val result = base.pow(exponent)
            stack.push(
                NumberType.FloatType("$result")
            )
        }
    }


    data class VariableReference(val ref: String) : Token(ref) {
        override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        }

        override fun onEvaluate(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
        }
    }

    data class VariableFunction(
        val name: String,
        var token: NumberType.FloatType = NumberType.FloatType("0.0"),
    ) : FunctionType(name, priority = 0) {
        override fun onParse(tokens: Queue<Token>, queue: Queue<Token>, stack: Stack<Token>) {
            if (!stack.isVariableDefined(this)) {
                if (tokens.peek() != AssignmentToken)
                    error("assignment '=' messing for variable '$name' ")
                tokens.poll() // remove assignment
                stack.add(this)
                while (tokens.isNotEmpty() && (tokens.peek() != SemicolonToken)) {
                    if (tokens.peek() is VariableFunction) {
                        val poll = tokens.poll() as VariableFunction
                        if (!queue.isVariableDefined(poll)) {
                            error("Variable undefined '${poll.name}'")
                        }
                        queue.add(VariableReference(poll.name))
                    } else
                        tokens.poll()?.onParse(tokens, queue, stack)
                }
                if (tokens.isEmpty() || tokens.peek() != SemicolonToken) {
                    error("messing semiColon ';' at end of '$name' ")
                }
                tokens.poll() // remove semicolon
                while (stack.isNotEmpty() && stack.peek() != this) {
                    queue.add(stack.pop())
                }
                queue.add(stack.pop())
            } else {
                // do nothing
            }
        }

        override fun calculateFunction(stack: Stack<Token>) {
            if (stack.isVariableDefined(this)) {
                stack.removeIf {
                    it is VariableFunction && it.name == this.name
                }
            }
            token = NumberType.FloatType(stack.pop().value)
            stack.push(this)
        }
    }
}

fun String.isMathFun(): Boolean {
    return this.matches(
        "(log)|(ln)|(exp)|(cos)|(sin)|(sqrt)|(\\^)".toRegex()
    )
}

fun String.toMathFun(): FunctionType {
    return when (this) {
        "log", "Log", "LOG" -> FunctionType.LogFunction
        "ln", "Ln", "LN" -> FunctionType.LnFunction
        "exp", "Exp", "EXP" -> FunctionType.ExpFunction
        "cos", "Cos", "COS" -> FunctionType.CosFunction
        "sin", "Sin", "SIN" -> FunctionType.SinFunction
        "sqrt", "Sqrt", "SQRT" -> FunctionType.SqrtFunction
        "^" -> FunctionType.PowFunction
        else -> error("Unsupported function '$this'")
    }
}

fun Collection<Token>.isVariableDefined(variableToken: Token): Boolean {
    return this.isNotEmpty() && this.find {
        it.value == variableToken.value
    } != null
}

fun String.isVariableName(): Boolean {
    return this.matches("([a-z,A-Z])".toRegex())
}

fun String.toVariableToken(): Token {
    return FunctionType.VariableFunction(this)
}
