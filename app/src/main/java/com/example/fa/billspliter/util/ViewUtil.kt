package com.example.fa.billspliter.util

import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.ui.adapter.NearbyAdapter
import com.example.fa.billspliter.ui.adapter.NearbyReceivedAdapter

class ViewUtil {

    fun resetRecyclerView(nearbyUser: ArrayList<DeviceData>) {
        val recycleAdapter: NearbyAdapter? = null
        recycleAdapter!!.setDevicedata(nearbyUser)
        recycleAdapter!!.notifyDataSetChanged()
    }

    fun resetRecyclerView(RBList: List<ReceivedBillEntity>, position: Int, recycleAdapter: NearbyReceivedAdapter?) {
        val dummy = RBList as ArrayList<ReceivedBillEntity>
        dummy.remove(RBList[position])
        recycleAdapter?.setData(dummy)
    }

}