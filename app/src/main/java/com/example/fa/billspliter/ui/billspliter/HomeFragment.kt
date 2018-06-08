package com.example.fa.billspliter.ui.billspliter


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.ui.adapter.*
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.connectionClients
import com.example.fa.billspliter.util.DateUtil
import com.example.fa.billspliter.util.DialogFactory
import com.example.fa.billspliter.util.ScreenShotClass
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.fragment_home_page.view.*


class HomeFragment : Fragment() {

    private var dateUtil =DateUtil()
    private var dialogFactory = DialogFactory()
    private var Service_ID:String = "com.example.fa.billspliter.ui.billspliter"
    private var NearbyStrategy: Strategy = Strategy.P2P_CLUSTER
    private lateinit var view1 : View
    private var screenShotClass:ScreenShotClass ?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        view1 = view
        view.checktax.setOnClickListener(
                {
                    if(view.checktax.isChecked) {
                        view.TaxRateLayout.visibility = View.VISIBLE
                    }
                    else {
                        view.TaxRateLayout.visibility = View.GONE
                        view.taxratetext.setText("0")
                    }

                })
        view.checkdiscount.setOnClickListener(
                {
                    if(view.checkdiscount.isChecked) {
                        view.DiscountLayout.visibility = View.VISIBLE
                    }
                    else {
                        view.DiscountLayout.visibility = View.GONE
                        view.DiscountText.setText("0")
                    }

                })
        screenShotClass = ScreenShotClass(context)
        view.OtherDeviceShareBtn.setOnClickListener({
            var bitmap:Bitmap = screenShotClass!!.takeScreenshot(view)
            screenShotClass!!.saveBitmap(bitmap)
            screenShotClass!!.sharebuttonIntent()
        })
        setBillTextAdapter(view)
        setTaxRateAdapter(view)
        setDiscountextAdapter(view)
        setTotalAmountTextWatcher(view)
        setPeopleTextWatcher(view)
        val taxRateBarAdapter:TaxRateBarAdapter = TaxRateBarAdapter(view.taxratetext,view.textamounttext,view.AmountText,view.TotalAmountMoneyText,view.DiscountText)
        view.taxratebar.setOnSeekBarChangeListener(taxRateBarAdapter)
        view.plusButton.setOnClickListener({
            val data = view.PeopleText.text.toString()
            val calculateddata = data.toInt()+1
            view.PeopleText.setText(calculateddata.toString())
        })
        view.minusButton.setOnClickListener({
            val data = view.PeopleText.text.toString()
            if(data.toInt()<=0) {
                view.PeopleText.setText("0")
            }
            else {
                val calculateddata = data.toInt()-1
                view.PeopleText.setText(calculateddata.toString())
            }
        })
        view.saveBtn.setOnClickListener({
            saveToDatabase(view)
        }
        )
        view.BluetoothShareBtn.setOnClickListener({
            if(TextUtils.isEmpty(view.AmountText.text.toString())) {
                Toast.makeText(context!!,"Please insert amount money . ",Toast.LENGTH_SHORT).show()
            }
            else {
                startDiscovery()
            }
        })

        return view
    }

    fun saveToDatabase(view:View) {
        if(TextUtils.isEmpty(view.AmountText.text.toString())) {
            Toast.makeText(context!!,"Please insert amount money . ",Toast.LENGTH_SHORT).show()
            return
        }
        val amount = view.AmountText.text.toString()
        val numPeople = view.PeopleText.text.toString()
        val tax = view.taxratetext.text.toString()
        val discount = view.DiscountText.text.toString()
        val totalAmount = view.TotalAmountMoneyText.text.toString()
        val eachPaid = view.BillAmountAfterSpilt.text.toString()
        val date = dateUtil.getDate()

        val entityData= BillEntity(null,amount,numPeople ,tax ,discount ,totalAmount , eachPaid , date,null)
        dialogFactory.saveToDbDialog(context!!,entityData).show()


    }
    private fun setBillTextAdapter(view:View) {
        val textWactcher:TextWatcher = BillAmountTextWatcherAdapter(
                view.TotalAmountMoneyText,
                view.taxratetext,
                view.textamounttext,
                view.AmountText,
                view.DiscountText
        )
        view.AmountText.addTextChangedListener(textWactcher)
    }

    private fun setTaxRateAdapter(view:View) {
        val textWatcher:TextWatcher = TaxRateTextWatcherAdapter(
                view.taxratebar,
                view.textamounttext,
                view.AmountText,
                view.TotalAmountMoneyText,
                view.taxratetext
        )
        view.taxratetext.addTextChangedListener(textWatcher)
    }

    private fun setDiscountextAdapter(view:View) {
        val textWatcher:TextWatcher = DiscountRateTextWatcherAdapter(
                view.DiscountText,
                view.TotalAmountMoneyText,
                view.AmountText,
                view.textamounttext
        )
        view.DiscountText.addTextChangedListener(textWatcher)
    }

    private fun setTotalAmountTextWatcher(view:View) {
        val textWatcher:TextWatcher = TotalAmountAdapter(
                view.TotalAmountMoneyText,
                view.PeopleText,
                view.BillAmountAfterSpilt
        )
        view.TotalAmountMoneyText.addTextChangedListener(textWatcher)
    }

    private fun setPeopleTextWatcher(view:View) {
        val textWatcher:TextWatcher = PersonTextWatcherAdapter(
                view.TotalAmountMoneyText,
                view.PeopleText,
                view.BillAmountAfterSpilt
        )
        view.PeopleText.addTextChangedListener(textWatcher)
    }

    private fun sendDataToAllConnected(view:View) :String{

        var combinedData:String = "${view.AmountText.text.toString()}"+
                ",${view.PeopleText.text.toString()}"+
                ",${view.taxratetext.text.toString()}" +
                ",${view.DiscountText.text.toString()}"+
                ",${view.TotalAmountMoneyText.text.toString()}"+
                ",${view.BillAmountAfterSpilt.text.toString()}"+
                ",${dateUtil.getDate()}"
        return combinedData

    }

     fun startDiscovery() {
          Log.d("test123","Connecting.....")
        connectionClients!!.startDiscovery(
                Service_ID,
                EndPointConnectionCallbackAdapter(context!!,sendDataToAllConnected(view1)),
                DiscoveryOptions(NearbyStrategy)
        ).addOnSuccessListener(object : OnSuccessListener<Void> {
            override fun onSuccess(p0: Void?) {
                Log.d("successful called", "discovering")
            }

        }).addOnFailureListener(object : OnFailureListener {
            override fun onFailure(it: Exception) {
                Log.d("Failed to called", "discovering"+ it.message)
            }
        })
    }

}
