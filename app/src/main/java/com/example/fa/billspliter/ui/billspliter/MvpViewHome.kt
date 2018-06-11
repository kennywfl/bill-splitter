package com.example.fa.billspliter.ui.billspliter

import android.view.View

interface  MvpViewHome{

    interface HomeFragment{
        fun initView (view : View)
        fun saveToDatabase(view:View)
        fun setBillTextAdapter(view:View)
        fun setTaxRateAdapter(view:View)
        fun setDiscountextAdapter(view:View)
        fun setTotalAmountTextWatcher(view:View)
        fun setPeopleTextWatcher(view:View)
        fun getSendData(view:View):String
    }
}