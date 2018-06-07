package com.example.fa.billspliter.ui.adapter

import android.app.Activity
import android.util.Log
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.google.android.gms.nearby.connection.*


class ConnectionLifeCycleCallBackAcceptAdapter:ConnectionLifecycleCallback {
    var connectionClients: ConnectionsClient?=null
    constructor(connectionClients:ConnectionsClient) : super(){
        this.connectionClients = connectionClients
    }

    override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
        when (result.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> {
                Log.d("device connected","device connected")
                AddDevice(endpointId)
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
        RemoveDevice(endpointId)
        Log.d("device removed","device removed")//To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        connectionClients!!.acceptConnection(endpointId,PayloadCallbackAdapter()) //To change body of created functions use File | Settings | File Templates.
    }

    public fun AddDevice(data:String){
        HomeActivity.ConnectedDevice!!.add(data)
    }

    public fun RemoveDevice(data:String){
        HomeActivity.ConnectedDevice!!.remove(data)
    }

}