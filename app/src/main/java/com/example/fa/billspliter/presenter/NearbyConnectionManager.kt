package com.example.fa.billspliter.presenter

import android.content.Context
import android.util.Log
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.data.model.UserData
import com.example.fa.billspliter.ui.adapter.ConnectionLifeCycleCallBackAcceptAdapter
import com.example.fa.billspliter.ui.adapter.EndPointConnectionCallbackAdapter
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.connectionClients
import com.example.fa.billspliter.util.ProgressDialogUtil
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class NearbyConnectionManager {

    constructor(){

    }


    public fun startAdvertising(userData: UserData, Service_ID:String, NearbyStrategy: Strategy) {
       connectionClients!!.startAdvertising(
                userData!!.name!!,
                Service_ID,
                ConnectionLifeCycleCallBackAcceptAdapter(connectionClients!!,null),
                AdvertisingOptions(NearbyStrategy)
        ).addOnSuccessListener(object: OnSuccessListener<Void> {
            override fun onSuccess(p0: Void?) {
                Log.d("successful called", "advertising")
            }

        }).addOnFailureListener(object: OnFailureListener {
            override fun onFailure(it: Exception) {
                Log.d("Failed to called", "advertising"+ it.message)
            }
        })
    }


    public fun startDiscovery(Service_ID:String, NearbyStrategy: Strategy,context: Context,data:String) {
        Log.d("test123","Connecting.....")
        connectionClients!!.startDiscovery(
                Service_ID,
                EndPointConnectionCallbackAdapter(context,data),
                DiscoveryOptions(NearbyStrategy)
        ).addOnSuccessListener(object : OnSuccessListener<Void> {
            override fun onSuccess(p0: Void?) {
                Log.d("successful called", "discovering")
            }

        }).addOnFailureListener(object : OnFailureListener {
            override fun onFailure(it: Exception) {
                Log.d("Failed to called", "discovering"+ it.message)
            }
        })
    }


    public fun sendPayLoad(endpointId: String,data:String){
        connectionClients!!.sendPayload(endpointId, Payload.fromBytes(data.toByteArray())
        ).addOnFailureListener(object: OnFailureListener {
            override fun onFailure(it: Exception) {
                Log.d("ConnectionLifeCycle", "Fail to send message . "+ it.message)
            }
        }).addOnCompleteListener({
            Log.d("completed ", "disconnect endpoint")
        })

    }


    public fun startConnect(deviceData: DeviceData,data: String) {
        ProgressDialogUtil.progressDialog.show()
        connectionClients!!.requestConnection(
                deviceData.NickName!!,
                deviceData.EndPointID!!,
                ConnectionLifeCycleCallBackAcceptAdapter(connectionClients!!,data)
        ).addOnSuccessListener(object : OnSuccessListener<Void> {
            override fun onSuccess(p0: Void?) {
                Log.d("connection connected", "connection connected")
            }
        }).addOnFailureListener(object : OnFailureListener {
            override fun onFailure(p0: Exception) {
                Log.d("connection failed", p0.message)
            }
        })
    }


}