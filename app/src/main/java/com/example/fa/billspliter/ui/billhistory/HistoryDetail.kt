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
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import kotlinx.android.synthetic.main.fragment_history_detail.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class HistoryDetail : Fragment() {

    var ImagePath:File ?=null
    var CheckPublishStatus:Boolean =false

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

        view.shareBtn.setOnClickListener({
                    val bitmap: Bitmap = takeScreenshot(view)
                    saveBitmap(bitmap)
                    sharebuttonIntent()
                }
        )
        view.publish.setOnClickListener{
            var combinedData:String = "Bill amount : RM  ${data.amount}"+"\n Number of people : ${data.numPeople}"+"\n Tax rate:  ${data.tax} %" + "\n Discount : ${data.discount} %"+"\n Total bill amount : RM  ${data.totalPaid}"+"\n Each  person paid : RM  ${data.eachPaid}"+"\n Issue date : ${data.date}"
            val mMessage =  Message(combinedData.toByteArray())
            if (CheckPublishStatus!=true){
                publish(mMessage)
                CheckPublishStatus =true
                Toast.makeText(context!!,"Published Message", Toast.LENGTH_SHORT).show()
            }else{
                unpublish(mMessage)
                CheckPublishStatus=false
                Toast.makeText(context!!,"Unpublished Message", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun sharebuttonIntent() {
        val uri: Uri = Uri.fromFile(ImagePath)
        val myIntent: Intent = Intent(Intent.ACTION_SEND)
        myIntent.setType("image/*")
        val shareBody:String = "Below Show the Bill Amount of the Bill and How many Each Person Should Pay"
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody)
        myIntent.putExtra(Intent.EXTRA_STREAM,uri)
        startActivity(Intent.createChooser(myIntent,"Share Using : "))
    }
    private fun takeScreenshot(view: View) : Bitmap {
        val rootView:View = view.rootView
        rootView.isDrawingCacheEnabled = true
        return rootView.drawingCache
    }

    private fun saveBitmap(bitmap: Bitmap) {

        val path:String = Environment.getExternalStorageDirectory().toString()
        ImagePath = File(path,"/DCIM/Screenshots/screenshot.png")

        Log.d("Path",ImagePath!!.path.toString())
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(ImagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
            fos.flush()
            fos.close()
        }
        catch (e: FileNotFoundException) {
            Log.e("Image share error:", e.message, e)
        }
        catch (e: IOException) {
            Log.e("Image share error:", e.message, e)
        }
    }

    private fun publish(mMessage : Message) {
        Nearby.getMessagesClient(context!!).publish(mMessage)
    }

    private fun unpublish(mMessage : Message) {
        Nearby.getMessagesClient(context!!).unpublish(mMessage)
    }

}
