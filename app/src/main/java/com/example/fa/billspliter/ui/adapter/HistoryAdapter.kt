package com.example.fa.billspliter.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.ui.billhistory.MvpViewHistory
import kotlinx.android.synthetic.main.history_rv_layout.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private  var c: Context
    private  var historyList: List<BillEntity>
    private var historyView : MvpViewHistory?=null

    constructor(c: Context, nearbyPlaceList: List<BillEntity> ,historyView: MvpViewHistory)  {
        this.c = c
        this.historyList = nearbyPlaceList
       this.historyView = historyView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.history_rv_layout, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = historyList[position]

        holder?.tv_id?.text = data.id.toString()
        holder?.tv_price?.text = data.amount
        holder?.tv_date?.text = data.date

        holder?.itemView?.setOnClickListener(View.OnClickListener {
            historyView?.onClick(data)
        });

        holder?.itemView?.setOnLongClickListener(View.OnLongClickListener {
            historyView?.onLongClick(data)
            true
        });

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tv_id: TextView
        var tv_price: TextView
        var tv_date:TextView

        init {
            tv_id = itemView.tv_id
            tv_price = itemView.tv_price
            tv_date = itemView.tv_date


        }

    }
}