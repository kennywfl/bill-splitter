package com.example.fa.billspliter.ui.adapter

import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil
import java.text.DecimalFormat

class TaxRateBarAdapter:SeekBar.OnSeekBarChangeListener {

    var taxratetv:TextView ?=null
    var taxamounttv:TextView ?=null
    var TotalAmount:EditText ?=null
    var cal:CalculationUtil ?=null
    var decimalFormat:DecimalFormat ?=null
    var TotalAmountPlusTax:TextView?=null
    var DiscountText:EditText?=null

    constructor(taxratetv: TextView?, taxamounttv: TextView?, TotalAmount: EditText?,TotalAmountPlusTax:TextView?,DiscountText:EditText?) {
        this.taxratetv = taxratetv
        this.taxamounttv = taxamounttv
        this.TotalAmount = TotalAmount
        this.TotalAmountPlusTax = TotalAmountPlusTax
        this.DiscountText=DiscountText
        cal = CalculationUtil()
        decimalFormat = DecimalFormat("###,###.00")
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        taxratetv!!.setText(progress.toString())

        var data =  ""
        if(TotalAmount!!.text.toString()!="") {
            val discountrate:String = DiscountText!!.text.toString()
            data = TotalAmount!!.text.toString()
            val calculateddata:Float = cal!!.CalculateTaxRate(data.toFloat(),progress.toFloat())
            val discountammount:Float = cal!!.CalculateTaxRate(data.toFloat(),discountrate.toFloat())
            taxamounttv!!.setText(roundTwoDecimals(calculateddata))
            val totalamountdata:Float = cal!!.addition(calculateddata,data.toFloat())
            val finalamountdata:Float = cal!!.subtraction(totalamountdata,discountammount)
            TotalAmountPlusTax!!.setText(roundTwoDecimals(finalamountdata))
        }
        else {
            taxamounttv!!.setText("0.00")
            TotalAmountPlusTax!!.setText("0.00")
        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    fun roundTwoDecimals(d: Float): String {
        val twoDForm = DecimalFormat("#.##")
        return twoDForm.format(d)
    }
}