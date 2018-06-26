package com.example.fa.billspliter.presenter

import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity

interface Presenter {

    interface RoomHelper {
        fun insertToDb(entityData: BillEntity)
        fun removeFromDb(entityData: BillEntity)
        fun getHistory()
        fun getHistorySaveServer()
        fun showList(historyList: List<BillEntity>)
        fun removeTable()
        fun removeFromFirebase(serverKey: String)
        fun insertToRDb(entityData: ReceivedBillEntity)
        fun removeFromRDb(entityData: ReceivedBillEntity)
        fun getRBillHistory()
        fun getRBillSaveServer()
        fun showRList(RBillList: List<ReceivedBillEntity>)
        fun removeRTable()


    }

    interface NearbyConnection {

    }
}