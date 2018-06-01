package com.example.fa.billspliter.ui.billhistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.ui.adapter.HistoryAdapter
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.util.DialogFactory
import kotlinx.android.synthetic.main.fragment_history.view.*


class History : Fragment(), MvpViewHistory {

    var roomHelper = RoomHelper(this)
    private lateinit var recycleView: RecyclerView
    private var dialogFactory = DialogFactory()
    private lateinit var preferenceHelper: PreferencesHelper
    private var loginType : String ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recycleView = view.recycleView
        preferenceHelper= PreferencesHelper(context!!)
        loginType = preferenceHelper.getType()

        if(loginType == "skip" ) {
            roomHelper.getHistory()
        }
        else{
            roomHelper.getHistorySaveServer()
        }

        return view
    }

      override fun setRecycleView(billList : List<BillEntity>) {
          try {
              val  recycleAdapter = HistoryAdapter(context!!, billList, this)
              val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
              recycleView.layoutManager = recycleLayout
              recycleView.adapter = recycleAdapter
          }
          catch (e:NullPointerException) {
              Log.d("History Adapter error:",e.toString())
          }

    }

    /* Handling on click event*/
    override fun onClick(billEntity: BillEntity) {
        var dataBundle: Bundle = Bundle()
        dataBundle.putSerializable("data", billEntity)
        findNavController().navigate(R.id.action_history_to_historyDetail,dataBundle)
    }

    /* Handling on long click event. */
    override fun onLongClick(billEntity: BillEntity ) {
        dialogFactory.createRemoveFavDialog(context!!,billEntity).show()
    }
    




}