package com.example.calculatorui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculatorui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var hasResult = false
    private var lastButtonPressed = ""
    private var shouldClearDisplay: Boolean = false
    private var shouldClearResult: Boolean = false
    private var operation: String = ""
    private var inputValue: String = ""
    private var resultValue: Double = 0.0

    private fun addInputValue (value: String): String {
        inputValue+= value
        return inputValue
    }

    private fun trimInputValue() {
        val isNotEmpty = inputValue.isNotEmpty()
        val isDotOnTheEnd = isNotEmpty && inputValue.get(inputValue.length - 1) == '.'
        if(isDotOnTheEnd) {
            displayNumber(subtractNumber())
        }
    }

    private fun subtractNumber (): String {
        if(inputValue.isNotEmpty()) {
            inputValue = inputValue.dropLast(1)
        }
        return inputValue
    }

    private fun displayNumber (value: String) {
        binding.tvNumber.text = value
    }


    private fun allClear() {
        hasResult = false
        lastButtonPressed = ""
        shouldClearDisplay = false
        shouldClearResult = false
        operation = ""
        inputValue = ""
        resultValue = 0.0

        displayNumber(this.inputValue)
    }

    private fun inputHasDot(): Boolean {
        return inputValue.contains('.')
    }

    private fun onNumberPress (value: String) {
        lastButtonPressed = value
        if(this.shouldClearDisplay) {
            this.shouldClearDisplay = false
            this.inputValue = ""
        }

        if(this.shouldClearResult) {
            this.shouldClearResult = false
            this.resultValue = 0.0
        }

        val isInputValueZero = inputValue == "0"

        if(isInputValueZero) {
            this.inputValue = value
            displayNumber(this.inputValue)
        } else {
            displayNumber(addInputValue(value))
        }
    }

    private fun onDeletePress () {
        displayNumber(subtractNumber())
    }

    private fun addInputToResult () {
        resultValue += inputValue.toDouble()
        inputValue = ""
        displayNumber(resultValue.toString())
        this.shouldClearDisplay = true
        hasResult = true
    }

    private fun subtractInputFromResult () {
        if(hasResult) {
            resultValue -= inputValue.toDouble()
        } else {
            resultValue = inputValue.toDouble()
        }
        inputValue = ""
        displayNumber(resultValue.toString())
        this.shouldClearDisplay = true
        hasResult = true
    }

    private fun multiplyInputOnResult () {
        if(hasResult) {
            resultValue *= inputValue.toDouble()
        } else {
            resultValue = inputValue.toDouble()
        }
        inputValue = ""
        displayNumber(resultValue.toString())
        this.shouldClearDisplay = true
        hasResult = true
    }

    private fun divideResultOnInput () {
        if(hasResult) {
            resultValue /= inputValue.toDouble()
        } else {
            resultValue = inputValue.toDouble()
        }
        inputValue = ""
        displayNumber(resultValue.toString())
        this.shouldClearDisplay = true
        hasResult = true
    }

    private fun performOperation (btn: String) {
        trimInputValue()
        val isPlus = operation == "+"
        val isMinus = operation == "-"
        val isMultiply = operation == "*"
        val isDivision = operation == "/"
        val isNotEmptyInput = inputValue.isNotEmpty()
        if(isNotEmptyInput) {
            lastButtonPressed = btn
            shouldClearResult = true
            if(isPlus) {
                addInputToResult()
            }
            if(isMinus) {
                subtractInputFromResult()
            }
            if(isMultiply){
                multiplyInputOnResult()
            }
            if(isDivision) {
                divideResultOnInput()
            }
            operation = ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val btn9 = binding.btn9
        val btn8 = binding.btn8
        val btn7 = binding.btn7
        val btn6 = binding.btn6
        val btn5 = binding.btn5
        val btn4 = binding.btn4
        val btn3 = binding.btn3
        val btn2 = binding.btn2
        val btn1 = binding.btn1
        val btn0 = binding.btn0
        val btnEqual = binding.btnEqual
        val btnAC = binding.btnAC
        val btnDiv = binding.btnDiv
        val btnMul = binding.btnMul
        val btnPlus = binding.btnPlus
        val btnMinus = binding.btnMinus
        val btnDot = binding.btnDot
        val btnDel = binding.btnDel


        btn9.setOnClickListener{
            onNumberPress("9")
        }

        btn8.setOnClickListener{
            onNumberPress("8")
        }

        btn7.setOnClickListener{
            onNumberPress("7")
        }

        btn6.setOnClickListener{
            onNumberPress("6")
        }

        btn5.setOnClickListener{
            onNumberPress("5")
        }

        btn4.setOnClickListener{
            onNumberPress("4")
        }

        btn3.setOnClickListener{
            onNumberPress("3")
        }

        btn2.setOnClickListener{
            onNumberPress("2")
        }

        btn1.setOnClickListener{
            onNumberPress("1")
        }

        btn0.setOnClickListener{
            if(inputValue !== "0") {
                onNumberPress("0")
            }
        }

        btnDel.setOnClickListener {
            onDeletePress()
        }

        btnAC.setOnClickListener{
            allClear()
        }

        btnDot.setOnClickListener {
            if(lastButtonPressed !== "." && !inputHasDot() && inputValue.isNotEmpty()) {
                lastButtonPressed = "."
                displayNumber(addInputValue("."))
            }
        }

        btnPlus.setOnClickListener {
            if(lastButtonPressed != "+") {
                if(operation.isNotEmpty()) {
                    performOperation("+")
                }
                shouldClearResult = false
                operation = "+"
                trimInputValue()
                if(inputValue.isNotEmpty()){
                    lastButtonPressed = "+"
                    addInputToResult()
                }
            }
        }

        btnMinus.setOnClickListener {
            if(lastButtonPressed != "-") {
                if(operation.isNotEmpty()) {
                    performOperation("-")
                }
                shouldClearResult = false
                operation = "-"
                trimInputValue()
                if(inputValue.isNotEmpty()){
                    lastButtonPressed = "-"
                    subtractInputFromResult()
                }
            }
        }

        btnMul.setOnClickListener {
            if(lastButtonPressed != "*") {
                if(operation.isNotEmpty()) {
                    performOperation("*")
                }
                shouldClearResult = false
                operation = "*"
                trimInputValue()
                if(inputValue.isNotEmpty()){
                    lastButtonPressed = "*"
                    multiplyInputOnResult()
                }
            }
        }

        btnDiv.setOnClickListener {
            if(lastButtonPressed != "/") {
                if(operation.isNotEmpty()) {
                    performOperation("/")
                }
                shouldClearResult = false
                operation = "/"
                trimInputValue()
                if(inputValue.isNotEmpty()){
                    lastButtonPressed = "/"
                    divideResultOnInput()
                }
            }
        }

        btnEqual.setOnClickListener {
            if(lastButtonPressed != "=") {
                performOperation("=")
            }
        }

    }
}