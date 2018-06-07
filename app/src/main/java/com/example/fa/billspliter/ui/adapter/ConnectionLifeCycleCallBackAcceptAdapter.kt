package com.example.fa.billspliter.ui.adapter

import android.util.Log
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener


class ConnectionLifeCycleCallBackAcceptAdapter:ConnectionLifecycleCallback {
    var connectionClients: ConnectionsClient?=null
    var data : String ?= null

    constructor(connectionClients:ConnectionsClient,data:String?) : super(){
        this.connectionClients = connectionClients
        this.data = data

    }

    override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
        when (result.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> {
                Log.d("device connected","device connected")
                if(data != null){
                    sendPayLoad(endpointId,data!!)
                }
            }
            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                Log.d("device rejected","device rejected")
            }
            ConnectionsStatusCodes.STATUS_ERROR -> {
                Log.d("device lost","device lost")
            }
        }
    }

    override fun onDisconnected(endpointId: String) {

    }

    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        connectionClients!!.acceptConnection(endpointId,PayloadCallbackAdapter()) //To change body of created functions use File | Settings | File Templates.
    }


   fun sendPayLoad(endpointId: String,data:String){
        connectionClients!!.sendPayload(endpointId, Payload.fromBytes(data.toByteArray())) .addOnSuccessListener(object: OnSuccessListener<Void> {
            override fun onSuccess(p0: Void?) {
                connectionClients!!.stopDiscovery()
            }
        }).addOnFailureListener(object: OnFailureListener {
            override fun onFailure(it: Exception) {
                Log.d("ConnectionLifeCycle", "Fail to send message . "+ it.message)
            }
        })

    }
}