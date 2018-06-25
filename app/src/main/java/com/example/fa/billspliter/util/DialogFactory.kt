package com.example.fa.billspliter.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.DeviceData
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.data.server.Firebase
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.ui.adapter.NearbyAdapter
import com.example.fa.billspliter.ui.adapter.NearbyReceivedAdapter
import com.example.fa.billspliter.ui.billspliter.HomeActivity
import com.google.android.gms.nearby.messages.Message
import kotlinx.android.synthetic.main.nearby_dialog.view.*


class DialogFactory
{
    private var  recycleAdapter:NearbyAdapter ?= null
    companion object {
        var contentdialog:AlertDialog ?=null
    }

    fun createTwoButtonDialog(context: Context,title:String , message: String ,clickListener:DialogInterface.OnClickListener):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        alertDialog.setTitle("$title")
                .setMessage("$message")
                .setPositiveButton("YES",clickListener)
                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
        return alertDialog.create()
    }
    fun showNearbyDialog(context:Context,nearbyUser: ArrayList<DeviceData>,data:String){
        val alertDialog = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.nearby_dialog, null)
        alertDialog.setView(dialogView)
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Select and send to nearby user.")
        val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        recycleAdapter =  NearbyAdapter(nearbyUser,data)
        dialogView.recycleView.layoutManager = recycleLayout
        dialogView.recycleView.adapter = recycleAdapter
        dialogView.recycleView.adapter
        contentdialog= alertDialog.create()
        contentdialog!!.show()
        dialogView.CloseBtn.setOnClickListener({
            HomeActivity.connectionClients!!.stopDiscovery()
            contentdialog!!.dismiss()
        })
    }
}