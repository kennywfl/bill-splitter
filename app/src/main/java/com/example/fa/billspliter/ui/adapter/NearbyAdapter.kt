package com.example.fa.billspliter.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.presenter.NearbyConnectionManager
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.connectionClients
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.nearby_rv_layout.view.*
import kotlin.coroutines.experimental.coroutineContext

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


    private  var nearbyUser: ArrayList<DeviceData>
    private var data : String ?= null
    private var nearbyConnectionManager = NearbyConnectionManager()

    constructor( nearbyUser: ArrayList<DeviceData>,data:String)  {
        this.nearbyUser = nearbyUser
        this.data = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.nearby_rv_layout, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Devicedata = nearbyUser[position]
        holder?.tv_name?.text = Devicedata.NickName
        holder.itemView?.setOnClickListener {
            nearbyConnectionManager.startConnect(Devicedata,data!!)
        }

    }

    override fun getItemCount(): Int {
        return nearbyUser.size
    }

    fun setDevicedata(nearbyUser:ArrayList<DeviceData>) {
        this.nearbyUser = nearbyUser
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name: TextView
        init {
            tv_name = itemView.tv_name
        }
    }

    }
