package com.example.fa.billspliter.ui.billhistory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import com.example.fa.billspliter.R

class BillHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_history)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

    }
    override fun onSupportNavigateUp()
            = findNavController(R.id.nav_host_bill).navigateUp()

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
       onBackPressed()
        return true

    }
}
