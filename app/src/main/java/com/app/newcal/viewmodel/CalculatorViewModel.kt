package com.app.newcal.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result

    fun onNumberClick(number: String) {
        _expression.value += number
    }

    fun onOperatorClick(operator: String) {
        if (_expression.value.isNotEmpty() && !isLastOperator()) {
            _expression.value += operator
        }
    }

    private fun isLastOperator(): Boolean {
        val last = _expression.value.lastOrNull()
        return last == '+' || last == '-' || last == '*' || last == '/' || last == '.'
    }

    fun onClear() {
        _expression.value = ""
        _result.value = ""
    }

    fun onDelete() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
        }
    }

    fun onEquals() {
        try {
            val evalResult = evaluate(_expression.value)
            _result.value = if (evalResult % 1.0 == 0.0) {
                evalResult.toLong().toString()
            } else {
                evalResult.toString()
            }
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }

    private fun evaluate(expr: String): Double {
        val tokens = mutableListOf<String>()
        var i = 0
        while (i < expr.length) {
            val c = expr[i]
            when {
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        sb.append(expr[i])
                        i++
                    }
                    tokens.add(sb.toString())
                    continue
                }
                c == '+' || c == '-' || c == '*' || c == '/' -> {
                    tokens.add(c.toString())
                }
                else -> {
                    // ignore unknown characters
                }
            }
            i++
        }

        val values = java.util.Stack<Double>()
        val ops = java.util.Stack<Char>()

        fun applyOp() {
            val op = ops.pop()
            val b = values.pop()
            val a = values.pop()
            val res = when (op) {
                '+' -> a + b
                '-' -> a - b
                '*' -> a * b
                '/' -> a / b
                else -> 0.0
            }
            values.push(res)
        }

        fun precedence(op: Char): Int = when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            else -> 0
        }

        for (token in tokens) {
            if (token[0].isDigit() || token[0] == '.') {
                values.push(token.toDouble())
            } else {
                val op = token[0]
                while (ops.isNotEmpty() && precedence(ops.peek()) >= precedence(op)) {
                    applyOp()
                }
                ops.push(op)
            }
        }
        while (ops.isNotEmpty()) {
            applyOp()
        }
        return values.pop()
    }
}
