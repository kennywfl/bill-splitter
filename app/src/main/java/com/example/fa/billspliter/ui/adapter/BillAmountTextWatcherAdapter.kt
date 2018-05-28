package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil

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
        if(BillAmountText!!.text.toString()!="")
        {
            var BillAmount:String = BillAmountText!!.text.toString()
            var RateOfTax:String = TaxRate!!.text.toString()
            var DiscountP:String = DiscountText!!.text.toString()
            var TaxAmount:Float = cal!!.CalculateTaxRate(BillAmount.toFloat(),RateOfTax.toFloat())
            var DiscounAmount:Float = cal!!.CalculateTaxRate(BillAmount.toFloat(),DiscountP.toFloat())
            TotalTaxAmountText!!.setText(TaxAmount.toString())
            var TotalBillAmount:Float = cal!!.addition(TaxAmount,BillAmount.toFloat())
            var FinalBillAmount:Float = cal!!.subtraction(TotalBillAmount,DiscounAmount)
            TotalBillAmountText!!.setText(FinalBillAmount.toString())
        }
        else
        {
            TotalBillAmountText!!.setText("0.00")
            TotalTaxAmountText!!.setText("0.00")
        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         //To change body of created functions use File | Settings | File Templates.
    }
}