package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil
import java.text.DecimalFormat
import kotlin.math.round

class BillAmountTextWatcherAdapter : TextWatcher {

    var BillAmountText:EditText ?=null
    var TotalBillAmountText: TextView ?=null
    var TaxRate : EditText ?=null
    var TotalTaxAmountText:EditText ?=null
    var DiscountText:EditText ?=null

    var cal:CalculationUtil ?=null

    constructor(TotalBillAmountText: TextView?, TaxRate: EditText?, TotalTaxAmountText: EditText?,BillAmountText:EditText?,DiscountText:EditText?) {
        this.TotalBillAmountText = TotalBillAmountText
        this.TaxRate = TaxRate
        this.TotalTaxAmountText = TotalTaxAmountText
        this.BillAmountText = BillAmountText
        this.DiscountText =DiscountText
        cal = CalculationUtil()
    }


    override fun afterTextChanged(s: Editable?) {

        if(BillAmountText!!.text.toString() =="00") {
            BillAmountText!!.setText("0")
        }

        if(BillAmountText!!.text.toString()!="") {
            val BillAmount:String = BillAmountText!!.text.toString()
            val RateOfTax:String = TaxRate!!.text.toString()
            val DiscountP:String = DiscountText!!.text.toString()
            val TaxAmount:Float = cal!!.CalculateTaxRate(BillAmount.toFloat(),RateOfTax.toFloat())
            val DiscounAmount:Float = cal!!.CalculateTaxRate(BillAmount.toFloat(),DiscountP.toFloat())
            TotalTaxAmountText!!.setText(roundTwoDecimals(TaxAmount))
            val TotalBillAmount:Float = cal!!.addition(TaxAmount,BillAmount.toFloat())
            val FinalBillAmount:Float = cal!!.subtraction(TotalBillAmount,DiscounAmount)
            TotalBillAmountText!!.setText(roundTwoDecimals(FinalBillAmount))
        }
        else {
            TotalBillAmountText!!.setText("0.00")
            TotalTaxAmountText!!.setText("0.00")
            DiscountText!!.setText("0")
        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    fun roundTwoDecimals(d: Float): String
    {
        val twoDForm = DecimalFormat("#.##")
        return twoDForm.format(d)
    }
}