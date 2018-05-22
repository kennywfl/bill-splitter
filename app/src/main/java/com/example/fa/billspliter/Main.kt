package com.example.fa.billspliter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onSupportNavigateUp()
            = findNavController(R.id.my_nav_host_fragment).navigateUp()
}
