package com.example.fa.billspliter

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fa.billspliter.data.local.BusStation
import com.google.android.gms.nearby.messages.Message
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_nearby.view.*


class Nearby : Fragment() {

    var NearbyText:TextView ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nearby, container, false)

        NearbyText = view.NearbyText
        return view
    }
     override fun onResume() {
        super.onResume()
        BusStation.bus.register(this)
    }

    override fun onPause() {
        super.onPause()
        BusStation.bus.unregister(this)
    }

   @Subscribe
    public fun receivedMessage(message:Message){
        NearbyText!!.setText(String(message.content))
    }

}
