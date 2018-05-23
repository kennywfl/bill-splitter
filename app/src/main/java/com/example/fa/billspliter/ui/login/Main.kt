package com.example.fa.billspliter.ui.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

import com.example.fa.billspliter.data.PreferencesHelper
import com.example.fa.billspliter.R

class Main : AppCompatActivity() {

    private lateinit var preferenceHelper: PreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onSupportNavigateUp()
            = findNavController(R.id.my_nav_host_fragment).navigateUp()



}
