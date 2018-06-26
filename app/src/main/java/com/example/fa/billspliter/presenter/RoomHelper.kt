package com.example.fa.billspliter.presenter


import com.example.fa.billspliter.ui.nearbymessage.MvpViewNearby
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.data.server.Firebase
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.db
import com.example.fa.billspliter.ui.billhistory.MvpViewHistory
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.rdb
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class RoomHelper : Presenter.RoomHelper {

    private var historyView: MvpViewHistory? = null
    private var firebase = Firebase(this)
    private var nearbyView: MvpViewNearby? = null

    constructor() {
    }

    constructor(historyView: MvpViewHistory) {
        this.historyView = historyView
    }

    constructor(nearbyView: MvpViewNearby) {
        this.nearbyView = nearbyView
    }

    /* Saving data to database. */
    override fun insertToDb(entityData: BillEntity) {
        async(CommonPool) {
            bg { db!!.billDao().addBill(entityData) }.await()
        }
    }

    /* Removing data from database*/
    override fun removeFromDb(entityData: BillEntity) {
        async(CommonPool) {
            bg { db!!.billDao().deleteBill(entityData) }.await()
        }
    }

    /* Retrieving the data from the database. */
    override fun getHistory() {
        async(UI) {
            val historyList = bg { db!!.billDao().getBillHistory() }.await()
            if (historyList.size > 0) {
                showList(historyList)
            }
        }
    }

    /*Automatically save to server , when the user login. */
    override fun getHistorySaveServer() {
        async(UI) {
            val historyList = bg { db!!.billDao().getBillHistory() }.await()
            if (historyList.size > 0) {
                firebase.saveToServer(historyList)
                removeTable()
            }
            firebase.getFromServer()
        }
    }

    /* Set the recycle view for history list.*/
    override fun showList(billList: List<BillEntity>) {
        historyView?.setRecycleView(billList)
    }

    /* Removing the table of history bill from the room table */
    override fun removeTable() {
        async(CommonPool) {
            bg { db!!.billDao().deleteTable() }.await()
        }
    }

    /*Remove bill entity from firebase.*/
    override fun removeFromFirebase(serverKey: String) {
        firebase.removeFromServer(serverKey!!)
    }

    /* Database operation for message received by Nearby API */
    /* Insert received bill into database*/
    override fun insertToRDb(entityData: ReceivedBillEntity) {
        async(CommonPool) {
            bg { rdb!!.RBillDao().addBill(entityData) }.await()
        }
    }

    /* Removing received bill from the database*/
    override fun removeFromRDb(entityData: ReceivedBillEntity) {
        async(CommonPool) {
            bg { rdb!!.RBillDao().deleteBill(entityData) }.await()
        }
    }

    /*Retrieve received bill from the database.*/
    override fun getRBillHistory() {
        async(UI) {
            val RBillList = bg { rdb!!.RBillDao().getBillHistory() }.await()
            if (RBillList.size > 0) {
                showRList(RBillList)
            }
        }
    }

    /*Automatically save received bill to server , when the  user login.*/
    override fun getRBillSaveServer() {
        async(UI) {
            val RBillList = bg { rdb!!.RBillDao().getBillHistory() }.await()
            if (RBillList.size > 0) {
                firebase.saveRBillToServer(RBillList)
                removeRTable()
            }
            firebase.getNearbyFromServer()
        }
    }

    /* Setting recylce view for nearby list*/
    override fun showRList(RBillList: List<ReceivedBillEntity>) {
        nearbyView?.setRecycleViewRBIll(RBillList)
    }

    /* Removing the table of received bill from the database */
    override fun removeRTable() {
        async(CommonPool) {
            bg { rdb!!.RBillDao().deleteTable() }.await()
        }
    }

}