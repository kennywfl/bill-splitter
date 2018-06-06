package com.example.fa.billspliter.presenter

import com.example.fa.billspliter.data.model.BillEntity

interface Presenter {

    interface RoomHelper {
        fun insertToDb(entityData: BillEntity)
        fun removeFromDb(entityData: BillEntity)
        fun getHistory()
        fun getHistorySaveServer()
        fun showList(historyList:List<BillEntity>)
        fun removeTable()

    }

    interface FirebaseHelper {
        fun saveNearbyName(name: String)
        fun removeNearbyName(name: String)
    }
}