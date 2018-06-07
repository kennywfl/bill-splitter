package com.example.fa.billspliter

import com.example.fa.billspliter.data.model.BillEntity

interface MvpViewNearby
{
    fun onClick(billEntity: BillEntity)
    fun onLongClick(billEntity: BillEntity)
    fun setRecycleView(billList : List<BillEntity>)
}