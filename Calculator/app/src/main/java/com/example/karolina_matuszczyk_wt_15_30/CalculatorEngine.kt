package com.example.karolina_matuszczyk_wt_15_30

import android.widget.TextView
import java.lang.Error
import java.util.*
import kotlin.math.sqrt

class CalculatorEngine(var display: TextView): CalculatorEngineInterface {
    private val OPS = "-+/*"

    private var addingNumber = false

    var expr = listOf<String>()
        set(value) {

            if (value.isEmpty()){
                display.text = "0"
            }
            else {
                display.text = value.joinToString("")
            }
            field = value
        }

    override fun isExpressionEmpty(): Boolean {
        return expr.isEmpty()
    }


    override fun addNumber(value: String) {
        if (!addingNumber) {
            addingNumber = true
            expr += value.toString()
            return
        }
        expr = expr.dropLast(1) + "${expr[expr.lastIndex]}$value"
    }

    override fun addOperation(value: String) {
        addingNumber = false
        expr += value
    }
    override fun changeOperation(value: String) {
        addingNumber = false
        expr = expr.dropLast(1) + value
    }

    override fun clear() {
        addingNumber = false
        expr = mutableListOf()
    }


    override fun sqrt() {
        var res = expr.joinToString("")
        expr = mutableListOf()
        expr += sqrt(res.toDouble()).toString()
    }


    override fun percent() {
        var res = expr.joinToString("")
        expr = mutableListOf()
        expr += (res.toDouble()/100).toString()
    }


    override fun evaluate() {
        if (expr.isEmpty()) return
        val s = Stack<Double>()
        expr = expr.dropLastWhile { OPS.indexOf(it) != -1 }
        var expression = infixToPostfix()
        print("expression "+expression)
        for (token in expression) {
            print(token)
            if (token != "+" && token != "-"
                && token != "*" && token != "/") {
                s.push(token.toDouble())
                continue;
            } else {
                val x = s.pop()
                val y = s.pop()
                when (token) {
                    "-" -> s.push(y - x)
                    "+" -> s.push(x + y)
                    "*" -> s.push(x * y)
                    "/" -> s.push(y / x)
                }
            }
        }

        expr = listOf(s.pop().toDouble().toString())
    }

    private fun infixToPostfix(): MutableList<String> {
        var parsed = mutableListOf<String>()
        val s = Stack<Int>()

        for (token in expr) {
            if (token.isEmpty()) continue
            val c = token[0]
            val idx = OPS.indexOf(c)

            if (idx != - 1) {
                if (s.isEmpty()) {
                    s.push(idx)
                }
                else {
                    while (!s.isEmpty()) {
                        val prec2 = s.peek() / 2
                        val prec1 = idx / 2
                        if (prec2 > prec1 || (prec2 == prec1)) {
                            parsed.add(OPS[s.pop()].toString())
                        }
                        else break
                    }
                    s.push(idx)
                }
            }
            else {
                parsed.add(token)
            }
        }

        while (!s.isEmpty()) parsed.add(OPS[s.pop()].toString())
        return parsed
    }

}