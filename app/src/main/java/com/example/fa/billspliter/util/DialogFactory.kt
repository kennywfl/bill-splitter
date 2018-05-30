package com.example.fa.billspliter.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.RoomHelper


class DialogFactory
{
    private var roomHelper =RoomHelper()
    /*fun createProgressDialog(context: Context): Dialog {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_view, null)
        val alertDialog = AlertDialog.Builder(context)
                .setView(dialogView)
               // .setCancelable(false)
        return alertDialog.create()

    }*/

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
        alertDialog.setTitle("ALERT!")
                .setMessage("Are you sure want to remove?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    roomHelper.removeFromDb(bill)
                    (context as Activity).recreate()
                })

                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        return alertDialog.create()
    }
    fun saveToDbDialog(context: Context,bill: BillEntity):Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        alertDialog.setTitle("ALERT!")
                .setMessage("Save To database?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    roomHelper.insertToDb(bill)
                    (context as Activity).recreate()
                    Toast.makeText(context!!,"Sucessfully saved . ",Toast.LENGTH_SHORT).show()

                })

                .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        return alertDialog.create()
    }

}