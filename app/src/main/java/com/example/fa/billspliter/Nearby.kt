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
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.presenter.Presenter
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.ui.adapter.EndPointConnectionCallbackAdapter
import com.example.fa.billspliter.ui.adapter.NearbyReceivedAdapter
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.connectionClients
import com.example.fa.billspliter.util.DialogFactory
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_nearby.view.*


class Nearby : Fragment() , MvpViewNearby{


    var roomHelper = RoomHelper(this)
    private lateinit var recycleView : RecyclerView
    private lateinit var preferenceHelper: PreferencesHelper
    private var loginType : String ?= null
    private var dialogFactory = DialogFactory()
    private var  recycleAdapter:NearbyReceivedAdapter ?= null
    private lateinit var existList :List<ReceivedBillEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nearby, container, false)
        recycleView = view.recycleView
        preferenceHelper= PreferencesHelper(context!!)
        loginType = preferenceHelper.getType()

        if(loginType == "skip" ) {
            roomHelper.getRBillHistory()
        }
        else{
            roomHelper.getRBillSaveServer()
        }
        return view
    }
    override fun onClick(RBList: List<ReceivedBillEntity> , position : Int) {
        dialogFactory.removeNearbyDialog(context!!,RBList,position,recycleAdapter!!).show()
    }
    override fun setRecycleViewRBIll(billList: List<ReceivedBillEntity>) {
        try {
            existList=billList
            recycleAdapter = NearbyReceivedAdapter(context!!, billList, this)
            val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            recycleView.layoutManager = recycleLayout
            recycleView.adapter = recycleAdapter
        }
        catch (e:NullPointerException) {
            Log.d("History Adapter error:",e.toString())
        }

    }



}
