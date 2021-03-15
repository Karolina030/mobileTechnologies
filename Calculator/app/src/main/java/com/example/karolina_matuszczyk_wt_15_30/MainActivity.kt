// Karolina Matuszczyk
//W implementacji brakuje tego by kalkulator działał po zmianie symboli operacji,
// obsługa kolejności działań zaimplementowana z wykorzystaniem odwrotnej notacji polskiej
// kalkulator obsługuje dodatkowo pierwiastki i procenty

package com.example.karolina_matuszczyk_wt_15_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var clear:Button
    internal lateinit var dot:Button
    internal lateinit var add:Button
    internal lateinit var substract:Button
    internal lateinit var multiply:Button
    internal lateinit var divide:Button
    internal lateinit var equal:Button
    internal lateinit var sqrt:Button
    internal lateinit var percent:Button

    internal lateinit var display:TextView

    private var decimalUsed = false
    private var lastOperation = false
    private var startOperation = false
    private var equalPressed = false

    internal lateinit var digits:Array<Button>
    private lateinit var calculatorEngine: CalculatorEngineInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clear = findViewById(R.id.clear)
        dot = findViewById(R.id.dot)
        add = findViewById(R.id.add)
        substract = findViewById(R.id.substract)
        multiply = findViewById(R.id.multiply)
        divide = findViewById(R.id.divide)
        sqrt = findViewById(R.id.sqrt)
        percent = findViewById(R.id.percent)

        equal = findViewById(R.id.equal)
        display = findViewById(R.id.result)
        calculatorEngine = CalculatorEngine(display)

        val buttonIDs = arrayOf(R.id.zero, R.id.one, R.id.two, R.id.three,
                R.id.four, R.id.five,R.id.six, R.id.seven, R.id.eight, R.id.nine)

        digits = (buttonIDs.map { id -> findViewById(id) as Button }).toTypedArray()
        digits.forEach { button -> button.setOnClickListener{i -> buttonPressed(i as Button)} }

        clear.setOnClickListener { clear() }
        dot.setOnClickListener { dot() }
        add.setOnClickListener { add() }
        substract.setOnClickListener { substract() }
        multiply.setOnClickListener { multiply() }
        divide.setOnClickListener { divide() }
        sqrt.setOnClickListener { sqrt() }
        percent.setOnClickListener { percent() }
        equal.setOnClickListener { equal() }
    }
    private fun equal() {
        equalPressed = true
        calculatorEngine.evaluate()
    }

    private fun percent() {
        if (startOperation) {
            calculatorEngine.percent()
        }
    }
    private fun sqrt() {
        if (startOperation) {
            calculatorEngine.sqrt()
        }
    }

    private fun clear() {
        calculatorEngine.clear()
        decimalUsed = false
        lastOperation = false
        startOperation = false
    }

    private fun dot() {
        if  (calculatorEngine.isExpressionEmpty()) {
            calculatorEngine.addNumber("0" + dot.text.toString())
            decimalUsed = true
        } else if (!decimalUsed) {
            decimalUsed = true
            calculatorEngine.addNumber(dot.text.toString())
        }
    }

    private fun add() {
        if (startOperation){
            if (lastOperation){
                calculatorEngine.addOperation(add.text.toString())
                lastOperation = false
                startOperation = true

            }
            else{
                calculatorEngine.changeOperation(add.text.toString())
                lastOperation = false
                startOperation = true
            }
            decimalUsed = false
        }
        equalPressed = false


    }

    private fun substract() {
        if (startOperation){
            if (lastOperation){
                calculatorEngine.addOperation(substract.text.toString())
                lastOperation = false
                startOperation = true
            }
            else{
                calculatorEngine.changeOperation(substract.text.toString())
                lastOperation = false
                startOperation = true
            }
            decimalUsed = false
        }
        equalPressed = false

    }

    private fun multiply() {
        if (startOperation){
            if (lastOperation){
                calculatorEngine.addOperation(multiply.text.toString())
                lastOperation = false
                startOperation = true
            }
            else{
                calculatorEngine.changeOperation(multiply.text.toString())
                lastOperation = false
                startOperation = true
            }
            decimalUsed = false
        }
        equalPressed = false

    }


    private fun divide() {
        if (startOperation){
            if (lastOperation){
                calculatorEngine.addOperation(divide.text.toString())
                lastOperation = false
                startOperation = true
            }
            else{
                calculatorEngine.changeOperation(divide.text.toString())
                lastOperation = false
                startOperation = true
            }
            decimalUsed = false
        }
        equalPressed = false

    }


    private fun buttonPressed(digit: Button) {
        println("pressed: "+ digit.text.toString())
        if (equalPressed){
            clear()
            equalPressed = false
        }
        calculatorEngine.addNumber(digit.text.toString())

        lastOperation = true
        startOperation = true

    }

}