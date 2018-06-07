package com.example.fa.billspliter.ui.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.example.fa.billspliter.util.DialogFactory
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback

class EndPointConnectionCallbackAdapter:EndpointDiscoveryCallback {

    var DeviceList:ArrayList<DeviceData> ?=null
    var DeviceListDialogFactory:DialogFactory ?=null

    override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        DeviceList!!.add(DeviceData(discoveredEndpointInfo.endpointName,endpointId))
        DeviceListDialogFactory!!.resetRecyclerView(DeviceList!!)
        Log.d("founded", endpointId + " founded");
    }

    override fun onEndpointLost(endpointId: String) {
        for (i in 0..DeviceList!!.count()-1){
            if(endpointId==DeviceList!![i].EndPointID){
                DeviceList!!.remove(DeviceList!![i])
                DeviceListDialogFactory!!.resetRecyclerView(DeviceList!!)
            }
        }
    }

    constructor(context:Context,data:String) : super() {
        DeviceList = ArrayList<DeviceData>()
        DeviceListDialogFactory = DialogFactory()
        DeviceListDialogFactory!!.showNearbyDialog(context,DeviceList!!,data)
    }
}