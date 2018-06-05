package com.example.fa.billspliter.presenter


import android.content.Context
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.server.Firebase
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.db
import com.example.fa.billspliter.ui.billhistory.MvpViewHistory
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.google.android.gms.nearby.messages.Message
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class RoomHelper : Presenter.RoomHelper {

    private var historyView : MvpViewHistory?=null
    private var firebase = Firebase(this)


    constructor() {
    }

    constructor(historyView: MvpViewHistory) {
        this.historyView=historyView
    }
    /* Saving data to database. */
    override fun insertToDb(entityData: BillEntity) {
        async(CommonPool) {
            bg { db!!.billDao().addBill(entityData) }.await()
        }
    }

    override fun removeFromDb(entityData: BillEntity) {
        async(CommonPool) {
            bg { db!!.billDao().deleteBill(entityData) }.await()
        }
    }    /* Retrieving the data from the database. */
    override fun getHistory()  {
        async(UI) {
            val historyList = bg { db!!.billDao().getBillHistory() }.await()
            if(historyList .size > 0) {
               showList(historyList)
            }
        }
    }
    override fun getHistorySaveServer() {
        async(UI) {
            val historyList = bg { db!!.billDao().getBillHistory() }.await()
            if(historyList .size > 0) {
                firebase.saveToServer(historyList)
                removeTable()
            }
            firebase.getFromServer()
        }
    }
    override fun showList(historyList: List<BillEntity>) {
        historyView?.setRecycleView(historyList)
    }

    override fun removeTable() {
        async(CommonPool) {
            bg { db!!.billDao().deleteTable() }.await()
        }
    }

    fun saveHost(name : String){
        firebase.saveName(name)
    }
    fun  removeHost(name : String){
        firebase.removeName(name)
    }
    fun getHostList(c : Context,name : String,mMessage : Message) {
        firebase.getHostName(c,name,mMessage)

    }

/*    fun insertNearbyPeople(nearbyPeople: NearbyPeopleEntity) {
        async(CommonPool) {
            bg {   HomeActivity.nearbyDB!!.NearbyDao().addNearby(nearbyPeople) }.await()
        }
    }

    fun getNearbyPeople() : List<NearbyPeopleEntity> {
        return nearbyDB!!.NearbyDao().getNearbyHistory()
    }*/
}