package com.example.fa.billspliter.ui.billhistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.ui.adapter.HistoryAdapter
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.util.DialogFactory
import kotlinx.android.synthetic.main.fragment_history.view.*


class History : Fragment(), MvpViewHistory {

    var roomHelper=RoomHelper(this)
    private lateinit var recycleView: RecyclerView
    private var dialogFactory = DialogFactory()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recycleView=view.recycleView
        roomHelper.getHistory()

        return view
    }

      override fun setRecycleView(billList : List<BillEntity>) {
        val  recycleAdapter = HistoryAdapter(context!!, billList, this)
        val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        recycleView.layoutManager = recycleLayout
        recycleView.adapter = recycleAdapter

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


    fun abc()
    {
        
    }





}
