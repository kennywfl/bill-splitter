package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil
import org.w3c.dom.Text

class TaxRateTextWatcherAdapter:TextWatcher {
    var TaxRateBar:SeekBar ?=null
    var TotalTaxAmount:EditText ?=null
    var BillAmountText:EditText?=null
    var TotalBillAmountText:TextView ?=null
    var TaxRate : EditText ?=null
    var cal: CalculationUtil?=null
    constructor(TaxRateBar: SeekBar?, TotalTaxAmount: EditText?, BillAmountText: EditText?, TotalBillAmountText: TextView?, TaxRate:EditText?) {
        this.TaxRateBar = TaxRateBar
        this.TotalTaxAmount = TotalTaxAmount
        this.BillAmountText = BillAmountText
        this.TotalBillAmountText = TotalBillAmountText
        this.TaxRate = TaxRate
        cal = CalculationUtil()
    }


    override fun afterTextChanged(s: Editable?) {
        if(TaxRate!!.text.toString()!="")
        {
            var TaxRateP:String = TaxRate!!.text.toString()
            if(TaxRateP.toInt()<0)
            {
                TaxRateBar!!.progress=0
            }
            else if(TaxRateP.toInt()>100)
            {
                TaxRateBar!!.progress=100
            }
            else
            {
                TaxRateBar!!.progress = TaxRateP.toInt()
            }
        }
        else
        {
            TaxRateBar!!.progress=0
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }
}