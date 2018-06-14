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
    private var roomHelper =RoomHelper()
    private lateinit var preferenceHelper: PreferencesHelper
    private var firebase = Firebase()
    private var  recycleAdapter:NearbyAdapter ?= null
    companion object {
        var contentdialog:AlertDialog ?=null
    }

    fun createExitDialog(context: Context):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        alertDialog.setTitle("ALERT!")
                .setMessage("Are you sure want to quit the application?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    (context as Activity).finish()
                })

                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        return alertDialog.create()
    }

    fun createRemoveFavDialog(context: Context,bill: BillEntity):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        preferenceHelper= PreferencesHelper(context)
        val loginType = preferenceHelper.getType()
        alertDialog.setTitle("ALERT!")
                .setMessage("Are you sure want to remove?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    if(loginType == "skip"){
                        roomHelper.removeFromDb(bill)
                    }
                    else {
                        firebase.removeFromServer(bill.serverKey!!)
                    }

                    (context as Activity).recreate()
                })

                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        return alertDialog.create()
    }
    fun removeNearbyDialog(context: Context,RBList: List<ReceivedBillEntity>,position : Int ,recycleAdapter:NearbyReceivedAdapter):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        preferenceHelper= PreferencesHelper(context)
        val loginType = preferenceHelper.getType()
        alertDialog.setTitle("ALERT!")
                .setMessage("Are you sure want to remove?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    if(loginType == "skip"){
                        roomHelper.removeFromRDb(RBList[position])
                    }
                    else {
                        firebase.removeRBill(RBList[position].serverKey!!)
                    }
                      resetRecyclerView(RBList,position,recycleAdapter)
                })

                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        return alertDialog.create()
    }
    fun saveToDbDialog(context: Context,bill: BillEntity):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        preferenceHelper= PreferencesHelper(context)
        val loginType = preferenceHelper.getType()
        alertDialog.setTitle("ALERT!")
                .setMessage("Save To database?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    if(loginType == "skip"){
                        roomHelper.insertToDb(bill)
                    }
                    else {
                        firebase.saveBill(bill)
                    }

                    (context as Activity).recreate()
                    Toast.makeText(context!!,"Sucessfully saved . ",Toast.LENGTH_SHORT).show()

                })

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

    fun resetRecyclerView(nearbyUser: ArrayList<DeviceData>){
        recycleAdapter!!.setDevicedata(nearbyUser)
        recycleAdapter!!.notifyDataSetChanged()
    }
    fun resetRecyclerView(RBList: List<ReceivedBillEntity>,position: Int,recycleAdapter:NearbyReceivedAdapter){
        var dummy = RBList as ArrayList<ReceivedBillEntity>
        dummy.remove(RBList[position])
        recycleAdapter.setData(dummy)
       // recycleAdapter!!.notifyDataSetChanged()
    }


}