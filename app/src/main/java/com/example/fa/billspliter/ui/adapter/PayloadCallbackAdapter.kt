package com.example.fa.billspliter.ui.adapter

import android.util.Log
import com.example.fa.billspliter.StringSpliter
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import java.nio.charset.StandardCharsets.UTF_8

class PayloadCallbackAdapter:PayloadCallback {
    constructor() : super()

    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        val receivedData:String = String(payload.asBytes()!!)
        StringSpliter().split(receivedData,"google")
        Log.d("received","received")
    }

    override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
        Log.d("updated","updated") //To change body of created functions use File | Settings | File Templates.
    }
}