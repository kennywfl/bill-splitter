package com.example.fa.billspliter.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.fa.billspliter.R




class DialogFactory
{

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
}