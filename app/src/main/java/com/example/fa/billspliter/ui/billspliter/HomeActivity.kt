package com.example.fa.billspliter.ui.billspliter

import android.arch.persistence.room.Room
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.HistoryDatabase
import com.example.fa.billspliter.ui.login.Main
import com.example.fa.billspliter.data.model.UserData
import com.example.fa.billspliter.ui.billhistory.BillHistory
import com.example.fa.billspliter.ui.billhistory.History
import com.example.fa.billspliter.util.DialogFactory
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private  var userData : UserData?= null
    private var mGoogleSignInClient : GoogleApiClient?= null
    private var dialogFactory = DialogFactory()
    private lateinit var preferenceHelper: PreferencesHelper
    private var loginType :String ?= null

    companion object {
        var db: HistoryDatabase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        db = Room.databaseBuilder(applicationContext, HistoryDatabase::class.java, "bill").allowMainThreadQueries().build()

        preferenceHelper= PreferencesHelper(this)

        userData= UserData(preferenceHelper.getName(), preferenceHelper.getEmail(), preferenceHelper.getUrl())
        loginType =preferenceHelper.getType()

        if(loginType == "google") {
            mGoogleSignInClient = GoogleApiClient.getAllClients().first()
        }
        nav_view.getHeaderView(0).tv_name.text=userData?.name
        nav_view.getHeaderView(0).tv_email.text=userData?.email

        if(userData?.url != "" && userData?.url != null) {
           Picasso.with(applicationContext).load(userData?.url).fit().into(nav_view.getHeaderView(0).imageView)
        }
        nav_view.menu.setGroupCheckable(0,false,false)

        var permissionWrite:Boolean = checkStoragePermission()

    }

    override fun onSupportNavigateUp()
            = findNavController(R.id.nav_home_fragment).navigateUp()

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.support_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reset -> {
               recreate()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.history -> {
              /*  val intent = Intent(applicationContext, BillHistory::class.java)
                startActivity(intent)*/
                Navigation.findNavController(this,R.id.nav_home_fragment).navigate(R.id.action_homePage_to_billHistory)
            }
            R.id.sign_out -> {
                signOut()
            }
            R.id.Exit -> {
                val exitDialog = dialogFactory.createExitDialog(this)
                exitDialog.show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun signOut() {
        preferenceHelper.clear()
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut();
        if(loginType == "google") {
            Auth.GoogleSignInApi.signOut(mGoogleSignInClient)
        }
        else if(loginType=="facebook"){
            LoginManager.getInstance().logOut();
        }
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }

    override fun recreate() {
        Navigation.findNavController(this,R.id.nav_home_fragment).navigate(R.id.action_homePage_self)
    }

    fun checkStoragePermission() : Boolean{
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 112)
            return false
        }
        else {
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 112) {
            if(grantResults.count()>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

                }
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

}
