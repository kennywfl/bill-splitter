package com.example.fa.billspliter.ui.billhistory

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.util.DialogFactory
import com.example.fa.billspliter.util.ScreenShotClass
import com.google.android.gms.nearby.messages.Message
import kotlinx.android.synthetic.main.fragment_history_detail.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class HistoryDetail : Fragment() {

    var screenShotClass:ScreenShotClass ?=null
    private lateinit var preferenceHelper: PreferencesHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_detail, container, false)
        val data = arguments?.getSerializable("data")  as BillEntity

        screenShotClass = ScreenShotClass(context)
        view.tv_amount.text="Bill amount : RM  ${data.amount}"
        view.tv_num_people.text="Number of people  ${data.numPeople}"
        view.tv_tax.text="Tex rate:  ${data.tax} %"
        view.tv_discount.text="Discount : ${data.discount} %"
        view.tv_total.text="Total bill amount : RM  ${data.totalPaid}"
        view.tv_each_paid.text="Each  person paid : RM  ${data.eachPaid}"
        view.tv_date.text="Issue date : ${data.date}"

        preferenceHelper= PreferencesHelper(context!!)
        view.shareBtn.setOnClickListener({
                    val bitmap: Bitmap = screenShotClass!!.takeScreenshot(view)
                    screenShotClass!!.saveBitmap(bitmap)
                    screenShotClass!!.sharebuttonIntent()
                }
        )
        return view
    }


}
