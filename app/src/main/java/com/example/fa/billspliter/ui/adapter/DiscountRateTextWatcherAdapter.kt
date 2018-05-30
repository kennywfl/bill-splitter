package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil
import java.text.DecimalFormat

class DiscountRateTextWatcherAdapter:TextWatcher
{

    var DiscountText:EditText?=null
    var TotalBillAmount:TextView ?=null
    var BillAmountText : EditText ?=null
    var cal : CalculationUtil ?=null
    var TaxAmountText:EditText?=null

    constructor(DiscountText: EditText?, TotalBillAmount: TextView?,BillAmountText:EditText?,TaxAmountText:EditText?) {
        this.DiscountText = DiscountText
        this.TotalBillAmount = TotalBillAmount
        this.BillAmountText = BillAmountText
        this.TaxAmountText=TaxAmountText
        cal = CalculationUtil()
    }


    override fun afterTextChanged(s: Editable?) {
        var BillAmountNum:String = ""
        var TaxAmount:String = ""
        var discount_amount:Float = 0f
        var AfterDiscount:Float = 0f
        var finalAmount:Float = 0f
        if(DiscountText!!.text.toString()!="") {
            var discount_rate: String = DiscountText!!.text.toString()
            if (discount_rate=="00") {
                discount_rate="0"
                DiscountText!!.setText("0")
                if(BillAmountText!!.text.toString()=="") {
                    return
                }
                BillAmountNum = BillAmountText!!.text.toString()
                TaxAmount= TaxAmountText!!.text.toString()
                discount_amount = cal!!.CalculateTaxRate(BillAmountNum.toFloat(),discount_rate.toFloat())
                AfterDiscount= cal!!.subtraction(BillAmountNum.toFloat(),discount_amount)
                finalAmount = cal!!.addition(AfterDiscount,TaxAmount.toFloat())

                TotalBillAmount!!.setText(roundTwoDecimals(finalAmount))
            }
            else {
                if(BillAmountText!!.text.toString()=="") {
                    return
                }
                BillAmountNum = BillAmountText!!.text.toString()
                TaxAmount= TaxAmountText!!.text.toString()
                discount_amount = cal!!.CalculateTaxRate(BillAmountNum.toFloat(),discount_rate.toFloat())
                AfterDiscount= cal!!.subtraction(BillAmountNum.toFloat(),discount_amount)
                finalAmount = cal!!.addition(AfterDiscount,TaxAmount.toFloat())

                TotalBillAmount!!.setText(roundTwoDecimals(finalAmount))
            }
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    fun roundTwoDecimals(d: Float): String {
        val twoDForm = DecimalFormat("#.##")
        return twoDForm.format(d)
    }
}