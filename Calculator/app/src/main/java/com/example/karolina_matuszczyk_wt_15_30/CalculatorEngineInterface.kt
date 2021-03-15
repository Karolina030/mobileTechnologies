package com.example.karolina_matuszczyk_wt_15_30

interface CalculatorEngineInterface {
    public fun addNumber(value: String)
    public fun addOperation(value: String)
    public fun changeOperation(value: String)
    public fun evaluate()
    public fun clear()
    public fun isExpressionEmpty(): Boolean
    public fun sqrt()
    public fun percent()


}