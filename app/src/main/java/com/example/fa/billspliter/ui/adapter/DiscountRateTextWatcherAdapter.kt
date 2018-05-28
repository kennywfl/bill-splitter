package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil

class DiscountRateTextWatcherAdapter:TextWatcher
{

    var DiscountText:EditText?=null
    var TotalBillAmount:TextView ?=null
    var BillAmountText : EditText ?=null
    var cal : CalculationUtil ?=null
    var TaxAmountText:EditText?=null

    constructor(DiscountText: EditText?, TotalBillAmount: TextView?,BillAmountText:EditText?,TaxAmountText:EditText?)
    {
        this.DiscountText = DiscountText
        this.TotalBillAmount = TotalBillAmount
        this.BillAmountText = BillAmountText
        this.TaxAmountText=TaxAmountText
        cal = CalculationUtil()
    }


    override fun afterTextChanged(s: Editable?)
    {
        if(DiscountText!!.text.toString()!="")
        {
            var discount_rate: String = DiscountText!!.text.toString()
            if(discount_rate.toInt()>100)
            {
                discount_rate="100"
                DiscountText!!.setText("100")
                var BillAmountNum:String = BillAmountText!!.text.toString()
                var TaxAmount:String = TaxAmountText!!.text.toString()
                var discount_amount:Float = cal!!.CalculateTaxRate(BillAmountNum.toFloat(),discount_rate.toFloat())
                var AfterDiscount:Float = cal!!.subtraction(BillAmountNum.toFloat(),discount_amount)
                var finalAmount:Float = cal!!.addition(AfterDiscount,TaxAmount.toFloat())

                TotalBillAmount!!.setText(finalAmount.toString())
            }
            else if (discount_rate.toInt()<0)
            {
                discount_rate="0"
                DiscountText!!.setText("0")
                var BillAmountNum:String = BillAmountText!!.text.toString()
                var TaxAmount:String = TaxAmountText!!.text.toString()
                var discount_amount:Float = cal!!.CalculateTaxRate(BillAmountNum.toFloat(),discount_rate.toFloat())
                var AfterDiscount:Float = cal!!.subtraction(BillAmountNum.toFloat(),discount_amount)
                var finalAmount:Float = cal!!.addition(AfterDiscount,TaxAmount.toFloat())

                TotalBillAmount!!.setText(finalAmount.toString())
            }
            else
            {
                var BillAmountNum:String = BillAmountText!!.text.toString()
                var TaxAmount:String = TaxAmountText!!.text.toString()
                var discount_amount:Float = cal!!.CalculateTaxRate(BillAmountNum.toFloat(),discount_rate.toFloat())
                var AfterDiscount:Float = cal!!.subtraction(BillAmountNum.toFloat(),discount_amount)
                var finalAmount:Float = cal!!.addition(AfterDiscount,TaxAmount.toFloat())

                TotalBillAmount!!.setText(finalAmount.toString())
            }
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         //To change body of created functions use File | Settings | File Templates.
    }
}