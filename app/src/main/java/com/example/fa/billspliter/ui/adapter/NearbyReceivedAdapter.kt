package com.example.fa.billspliter.ui.adapter

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.fa.billspliter.MvpViewNearby
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.ReceivedBillEntity
import com.example.fa.billspliter.ui.billhistory.MvpViewHistory
import kotlinx.android.synthetic.main.nearby_receive_rv_layout.view.*

class NearbyReceivedAdapter : RecyclerView.Adapter<NearbyReceivedAdapter.ViewHolder> {


    private  var c: Context
    private  var RBList: List<ReceivedBillEntity>
    private var nearbyView : MvpViewNearby?=null

    constructor(c: Context, RBList: List<ReceivedBillEntity>, nearbyView: MvpViewNearby)  {
        this.c = c
        this.RBList = RBList
        this.nearbyView = nearbyView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.nearby_receive_rv_layout, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = RBList[position]
        holder?.tv_amount?.text ="Bill amount : RM  ${data.amount}"
        holder?.tv_num_people?.text = "Number of people  ${data.numPeople}"
        holder?.tv_tax?.text ="Tex rate:  ${data.tax} %"
        holder?.tv_discount?.text ="Discount : ${data.discount} %"
        holder?.tv_total?.text ="Total bill amount : RM  ${data.totalPaid}"
        holder?.tv_each_paid?.text ="Each  person paid : RM  ${data.eachPaid}"
        holder?.tv_date?.text = "Issue date : ${data.date}"

        holder?.shareBtn.setOnClickListener(View.OnClickListener {
            nearbyView?.onClick(RBList,position)
        });
    }

    override fun getItemCount(): Int {
        return RBList.size
    }
    fun setData(RBList: List<ReceivedBillEntity>) {
        this.RBList = RBList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_amount: TextView
        var tv_num_people: TextView
        var tv_tax:TextView
        var tv_discount: TextView
        var tv_total: TextView
        var tv_each_paid:TextView
        var tv_date:TextView
        var shareBtn: FloatingActionButton
        init {
            tv_amount = itemView.tv_amount
            tv_num_people = itemView.tv_num_people
            tv_tax = itemView.tv_tax
            tv_discount = itemView.tv_discount
            tv_total = itemView.tv_total
            tv_each_paid = itemView.tv_each_paid
            tv_date = itemView.tv_date
            shareBtn =itemView.shareBtn
        }

    }
}