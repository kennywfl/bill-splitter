package com.example.fa.billspliter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fa.billspliter.data.local.BusStation
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.Presenter
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.ui.adapter.EndPointConnectionCallbackAdapter
import com.example.fa.billspliter.ui.adapter.NearbyReceivedAdapter
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_nearby.view.*


class Nearby : Fragment() , MvpViewNearby{


    var NearbyText:TextView ?=null
    var roomHelper = RoomHelper(this)
    private var Service_ID:String = "com.example.fa.billspliter.ui.billspliter"
    private var NearbyStrategy: Strategy = Strategy.P2P_CLUSTER
    private lateinit var recycleView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nearby, container, false)
        recycleView = view.recycleView
        roomHelper.getNearbySave()
        startDiscovery()
        return view
    }
    override fun onClick(billEntity: BillEntity) {
    }

    override fun onLongClick(billEntity: BillEntity) {
    }

    override fun setRecycleView(billList: List<BillEntity>) {
        try {
            val  recycleAdapter = NearbyReceivedAdapter(context!!, billList, this)
            val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            recycleView.layoutManager = recycleLayout
            recycleView.adapter = recycleAdapter
        }
        catch (e:NullPointerException) {
            Log.d("History Adapter error:",e.toString())
        }

    }


    public fun startDiscovery() {
        HomeActivity.ConnectionClients!!.startDiscovery(
                Service_ID,
                EndPointConnectionCallbackAdapter(context!!),
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

}
