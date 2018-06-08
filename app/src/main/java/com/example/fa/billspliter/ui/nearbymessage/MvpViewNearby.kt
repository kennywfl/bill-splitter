package com.example.fa.billspliter.ui.nearbymessage

import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity

interface MvpViewNearby
{
    fun onClick(RBList: List<ReceivedBillEntity> , position : Int)

    fun setRecycleViewRBIll(billList : List<ReceivedBillEntity>)
}