package com.example.fa.billspliter.data.server

import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity

interface MvpModelServer {
    /*Saving bill into received to the server. */
    fun saveBill(bill: BillEntity)

    fun saveToServer(historyList: List<BillEntity>)
    fun getFromServer()
    fun removeFromServer(serverKey: String)

    /*Saving received bill to the server */
    fun saveNearbyBill(bill: ReceivedBillEntity)

    fun saveRBillToServer(RBillList: List<ReceivedBillEntity>)
    fun getNearbyFromServer()
    fun removeRBill(serverKey: String)


}