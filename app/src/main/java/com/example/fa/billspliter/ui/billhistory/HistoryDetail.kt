package com.example.fa.billspliter.ui.billhistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import kotlinx.android.synthetic.main.fragment_history_detail.view.*


class HistoryDetail : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_detail, container, false)
        val data = arguments?.getSerializable("data")  as BillEntity

        view.tv_amount.text="Bill amount : RM  ${data.amount}"
        view.tv_num_people.text="Number of people  ${data.numPeople}"
        view.tv_tax.text="Tex rate:  ${data.tax} %"
        view.tv_discount.text="Discount : ${data.discount} %"
        view.tv_total.text="Total bill amount : RM  ${data.totalPaid}"
        view.tv_each_paid.text="Each  person paid : RM  ${data.eachPaid}"
        view.tv_date.text="Issue date : ${data.date}"
        return view
    }

}
