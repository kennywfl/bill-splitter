package com.example.fa.billspliter.util

open class CalculationUtil {
    constructor()

    public fun addition(num1:Float,num2:Float) : Float
    {
        return num1 + num2
    }


    public fun CalculateTaxRate(TotalValue:Float,TaxRate:Float) : Float
    {
        return TotalValue * TaxRate / 100
    }

    public fun CalculateAverage(TotalAmount:Float,NumberOfPeople:Float):Float
    {
        return TotalAmount/NumberOfPeople
    }
}