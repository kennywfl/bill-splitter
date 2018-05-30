package com.example.fa.billspliter.ui.billhistory

import com.example.fa.billspliter.data.model.BillEntity

interface MvpViewHistory
{
    fun onClick(billEntity: BillEntity)
    fun onLongClick(billEntity: BillEntity)
    fun setRecycleView(billList : List<BillEntity>)
}