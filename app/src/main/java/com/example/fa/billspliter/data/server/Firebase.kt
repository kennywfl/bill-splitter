package com.example.fa.billspliter.data.server

import android.content.Context
import android.util.Log
import com.example.fa.billspliter.data.model.BillEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.fa.billspliter.presenter.Presenter
import com.example.fa.billspliter.util.DialogFactory
import com.google.android.gms.nearby.messages.Message
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class Firebase
{
    val id = FirebaseAuth.getInstance().uid
    private var roomPresenter : Presenter.RoomHelper ?=null

    constructor(){}

    constructor(roomPresenter: Presenter.RoomHelper) {
        this.roomPresenter=roomPresenter
    }

    fun saveBill(bill : BillEntity) {
        val historyDB = FirebaseDatabase.getInstance().getReference("History").child(id!!)
        val key = historyDB.push().key
        bill.serverKey = key
        historyDB.child(key!!).setValue(bill)
    }
    fun saveNearbyBill(bill : BillEntity) {
        val nearbyBillDB = FirebaseDatabase.getInstance().getReference("NearbyBill").child(id!!)
        val key = nearbyBillDB.push().key
        bill.serverKey = key
        nearbyBillDB.child(key!!).setValue(bill)
    }

    fun saveToServer(historyList : List<BillEntity>) {

        val historyDB = FirebaseDatabase.getInstance().getReference("History").child(id!!)
        for (i in  historyList.indices){
            val key = historyDB.push().key
            historyList[i].serverKey = key
            historyDB.child(key!!).setValue(historyList[i])
        }
    }
    fun getFromServer() {
        val historyDB = FirebaseDatabase.getInstance().getReference("History").child(id!!)
        historyDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val arrayContainer = ArrayList<BillEntity>()
                for (ds in dataSnapshot.children){
                    val bill = ds.getValue(BillEntity::class.java)!!
                    arrayContainer.add(bill)
                }
                if(!arrayContainer.isEmpty()) {
                    roomPresenter!!.showNearbyList(arrayContainer)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    fun getNearbyFromServer() {
        val nearbyBillDB = FirebaseDatabase.getInstance().getReference("NearbyBill").child(id!!)
        nearbyBillDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val arrayContainer = ArrayList<BillEntity>()
                for (ds in dataSnapshot.children){
                    val bill = ds.getValue(BillEntity::class.java)!!
                    arrayContainer.add(bill)
                }

                if(!arrayContainer.isEmpty()) {
                    roomPresenter!!.showNearbyList(arrayContainer)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    fun removeFromServer(serverKey : String) {
        val historyDB = FirebaseDatabase.getInstance().getReference("History").child(id!!).child(serverKey)
        historyDB.removeValue()
    }

    fun saveName(name : String) {
        val nameDB = FirebaseDatabase.getInstance().getReference("Nearby")
        val key = nameDB.push().key
        nameDB.child(key!!).setValue(name)
    }
    fun removeName(name : String) {
        val nameDB = FirebaseDatabase.getInstance().getReference("Nearby")
        nameDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children){
                    val savedName = ds.getValue(String::class.java)!!
                    if (savedName == name) {
                        val key = ds.key
                        Log.d("key123"," $key + $name")
                        nameDB.child(key!!).removeValue()
                    }

                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun getHostName(context : Context,name : String,mMessage : Message) {
         val dialogFactory = DialogFactory()
        val nameDB = FirebaseDatabase.getInstance().getReference("Nearby")
        nameDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hostList = ArrayList<String>()
                for (ds in dataSnapshot.children){
                    val savedName = ds.getValue(String::class.java)!!
                    if (savedName != name) {
                        hostList.add(savedName)
                    }
                }
              //dialogFactory.showNearbyDialog(context,hostList,mMessage).show()

            }

        })
    }
}