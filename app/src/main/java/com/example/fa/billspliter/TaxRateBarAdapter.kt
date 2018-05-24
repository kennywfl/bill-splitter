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

    constructor(taxratetv: TextView?, taxamounttv: TextView?, TotalAmount: EditText?) {
        this.taxratetv = taxratetv
        this.taxamounttv = taxamounttv
        this.TotalAmount = TotalAmount
        cal = CalculationUtil()
        decimalFormat = DecimalFormat("###,###.00")
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        taxratetv!!.setText(progress.toString())
        if(TotalAmount!!.text.toString()!="")
        {
            var data =  TotalAmount!!.text.toString()
            var calculateddata:Float = cal!!.CalculateTaxRate(data.toFloat(),progress.toFloat())
            taxamounttv!!.setText(calculateddata.toString())
       }
        else
        {
            taxamounttv!!.setText("0.00")
        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
         //To change body of created functions use File | Settings | File Templates.
    }
}