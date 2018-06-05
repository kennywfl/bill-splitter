package com.example.fa.billspliter.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.NearbyPeopleEntity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import kotlinx.android.synthetic.main.nearby_rv_layout.view.*

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


    private  var c: Context
    private  var nearbyUser: List<NearbyPeopleEntity>
    private  var mMessage : Message

    var count = 1

    constructor(c: Context, nearbyUser: List<NearbyPeopleEntity>,mMessage : Message)  {
        this.c = c
        this.nearbyUser = nearbyUser
        this.mMessage=mMessage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.nearby_rv_layout, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = nearbyUser[position]
        holder?.tv_name?.text = data.name

        holder?.itemView?.setOnClickListener(View.OnClickListener {
            val newMessage = Message(mMessage.content,data.name)
            publish(newMessage)

        });
    }

    override fun getItemCount(): Int {
        return nearbyUser.size
    }

    fun publish(mMessage : Message) {
        Nearby.getMessagesClient(c).publish(mMessage)
        Toast.makeText(c,"Sucessfully publish.",Toast.LENGTH_SHORT).show()
    }

    fun unpublish(mMessage : Message) {
        Nearby.getMessagesClient(c).unpublish(mMessage)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name: TextView


        init {
            tv_name = itemView.tv_name

        }

    }
}