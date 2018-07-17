package com.example.fa.billspliter.ui.adapter

import android.content.Context
import android.util.Log
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.util.DialogFactory
import com.example.fa.billspliter.util.ViewUtil
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback

class EndPointConnectionCallbackAdapter : EndpointDiscoveryCallback {

    var DeviceList: ArrayList<DeviceData>? = null
    var viewUtil = ViewUtil()
    val dialogfactory = DialogFactory()

    override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        DeviceList!!.add(DeviceData(discoveredEndpointInfo.endpointName, endpointId))
        viewUtil!!.resetRecyclerView(DeviceList!!)
        Log.d("founded", endpointId + " founded");
    }

    override fun onEndpointLost(endpointId: String) {
        for (i in 0..DeviceList!!.count() - 1) {
            if (endpointId == DeviceList!![i].EndPointID) {
                DeviceList!!.remove(DeviceList!![i])
                viewUtil!!.resetRecyclerView(DeviceList!!)
            }
        }
    }

    constructor(context: Context, data: String) : super() {
        DeviceList = ArrayList<DeviceData>()
        dialogfactory!!.showNearbyDialog(context, DeviceList!!, data)
    }
}