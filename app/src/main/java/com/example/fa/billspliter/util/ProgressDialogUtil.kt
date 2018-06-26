package com.example.fa.billspliter.util

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogUtil {
    /* class that control the progress dialog of the app*/
    companion object {
        lateinit var progressDialog: ProgressDialog
    }

    constructor(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Loading...")
    }

}