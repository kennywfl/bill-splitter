package com.example.fa.billspliter.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.fa.billspliter.util.CalculationUtil
import java.text.DecimalFormat

class TotalAmountAdapter : TextWatcher {

    var TotalBillAmountText: TextView? = null
    var NumberOfPerson: EditText? = null
    var AfterSplitText: TextView? = null
    var cal: CalculationUtil? = null

    constructor(TotalBillAmountText: TextView?, NumberOfPerson: EditText?, AfterSplitText: TextView?) {
        this.TotalBillAmountText = TotalBillAmountText
        this.NumberOfPerson = NumberOfPerson
        this.AfterSplitText = AfterSplitText
        cal = CalculationUtil()
    }


    override fun afterTextChanged(s: Editable?) {
        val TotalBillAmountData: String = TotalBillAmountText!!.text.toString()
        val NumberOfPersonData: String = NumberOfPerson!!.text.toString()
        val AfterSplitAmount = cal!!.CalculateAverage(TotalBillAmountData.toFloat(), NumberOfPersonData.toFloat())
        AfterSplitText!!.setText(roundTwoDecimals(AfterSplitAmount))
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