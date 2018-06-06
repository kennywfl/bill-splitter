package com.example.fa.billspliter.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.example.fa.billspliter.R
import com.example.fa.billspliter.ui.login.Main
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.coroutines.experimental.bg


class SplashScreen : AppCompatActivity() {

    val REQUEST_LOCATION_CODE = 99
    val REQUEST_WRITNG_CODE = 112
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)

        async (UI){
            bg{ checkStoragePermission()}
            var permission= bg {checkLocationPermission()  }
            if(permission.await()) {
                delay(2000)
                val intent = Intent(applicationContext, Main::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==REQUEST_LOCATION_CODE) {
            if(grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(applicationContext, Main::class.java)
                startActivity(intent)
                finish()
            }
            else if(requestCode == REQUEST_WRITNG_CODE) {
                if(grantResults.count()>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

                    }
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
            }
        }
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
            return false;
        }
        return true
    }

    fun checkStoragePermission() : Boolean{
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITNG_CODE)
            return false
        }
        else {
        }
        return true
    }
}