package com.example.fa.billspliter.ui.adapter

import android.util.Log
import com.example.fa.billspliter.util.StringSpliter
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

class PayloadCallbackAdapter:PayloadCallback {
    var loginType : String ? = null

    constructor() : super(){
    }
    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        val receivedData:String = String(payload.asBytes()!!)
        StringSpliter().split(receivedData)
        Log.d("received","received")
    }

    override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
        Log.d("updated","updated") //To change body of created functions use File | Settings | File Templates.
    }
}