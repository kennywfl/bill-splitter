package com.example.fa.billspliter

import android.util.Log
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.server.Firebase
import com.example.fa.billspliter.presenter.RoomHelper

class StringSpliter{
    private var roomHelper = RoomHelper()
    private val firebaseHelper = Firebase()

    fun split(string : String , loginType : String) {
       val data = string.split(",")
       val entityData= BillEntity(null,data[0],data[1] ,data[2] ,data[3] ,data[4] , data[5] , data[6],null)
        if(loginType == "skip"){

         //  roomHelper.insertToDb(entityData)
        }
        else {
            Log.d("test123","Sucess12")
            firebaseHelper.saveNearbyBill(entityData)
        }

    }
}