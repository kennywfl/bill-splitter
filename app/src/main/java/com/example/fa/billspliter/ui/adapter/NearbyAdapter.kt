package com.example.fa.billspliter.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import kotlinx.android.synthetic.main.nearby_rv_layout.view.*

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


    private  var activity: Activity
    private  var nearbyUser: ArrayList<DeviceData>
    var count = 1

    constructor(activity: Activity, nearbyUser: ArrayList<DeviceData>)  {
        this.activity = activity
        this.nearbyUser = nearbyUser
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.nearby_rv_layout, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = nearbyUser[position]
        holder?.tv_name?.text = data.NickName
        holder.tv_name.setOnClickListener {

            (activity as HomeActivity).startConnect(data)
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