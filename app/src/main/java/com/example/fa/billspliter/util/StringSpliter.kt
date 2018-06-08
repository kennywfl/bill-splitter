package com.example.fa.billspliter.util

import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.data.server.Firebase
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.loginType

class StringSpliter{
    private var roomHelper = RoomHelper()
    private val firebaseHelper = Firebase()

    fun split(string : String ) {
       val data = string.split(",")
        if(loginType == "skip"){
            val entityData= ReceivedBillEntity(null,data[0],data[1] ,data[2] ,data[3] ,data[4] , data[5] , data[6],null)
          roomHelper.insertToRDb(entityData)
        }
        else {
            val entityData= ReceivedBillEntity(null,data[0],data[1] ,data[2] ,data[3] ,data[4] , data[5] , data[6],null)
            firebaseHelper.saveNearbyBill(entityData)
        }

    }
}