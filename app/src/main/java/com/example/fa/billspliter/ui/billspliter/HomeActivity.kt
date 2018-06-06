package com.example.fa.billspliter.ui.billspliter

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.R
import com.example.fa.billspliter.StringSpliter
import com.example.fa.billspliter.data.model.HistoryDatabase
import com.example.fa.billspliter.ui.login.Main
import com.example.fa.billspliter.data.model.UserData
import com.example.fa.billspliter.data.local.BusStation
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.util.DialogFactory
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private var mMessageListener = MessageListener()
    private  var userData : UserData?= null
    private var mGoogleSignInClient : GoogleApiClient?= null
    private var dialogFactory = DialogFactory()
    private lateinit var preferenceHelper: PreferencesHelper
    private var loginType :String ?= null
    private var previousMenuItem :  MenuItem ?=null
    private  var googleApiClient: GoogleApiClient? = null
    private var roomHelper = RoomHelper()

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
              buildGoogleApiClient()
    }

    override fun onStart() {
        super.onStart()
        mMessageListener = object: MessageListener() {
            override fun onFound(message: Message) {
                if(message.type==userData?.name ) {
                    Log.d("test123", "Sucess publish")
                    StringSpliter().split(String(message.content),loginType!!)
                  //  BusStation.bus.post(message)
                }
            }
            override fun onLost(message: Message) {
                }
        }
    }

    override fun onResume() {
        super.onResume()
        nav_view.menu.getItem(0).isChecked=true
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
                if(previousMenuItem != null) {
                    Navigation.findNavController(this,R.id.nav_home_fragment).popBackStack(R.id.nav_home_fragment,true)
                }
               recreate()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if(previousMenuItem != null) {
            Navigation.findNavController(this,R.id.nav_home_fragment).popBackStack(R.id.nav_home_fragment,true)
        }
        when (item.itemId) {
            R.id.home -> {
                previousMenuItem = null
            }
            R.id.history -> {
                previousMenuItem = null
                Navigation.findNavController(this,R.id.nav_home_fragment).navigate(R.id.action_homePage_to_billHistory)
            }
            R.id.nearby -> {
                toolbar.menu.findItem(R.id.reset).title="Back"
                previousMenuItem = item
                Navigation.findNavController(this,R.id.nav_home_fragment).navigate(R.id.action_homePage_to_nearby2)
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
        if(loginType == "google") {
            FirebaseAuth.getInstance().signOut()
            Auth.GoogleSignInApi.signOut(mGoogleSignInClient)
        }
        else if(loginType=="facebook"){
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut();
        }
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }

    override fun recreate() {
        Navigation.findNavController(this,R.id.nav_home_fragment).navigate(R.id.action_homePage_self)
        toolbar.menu.findItem(R.id.reset).title="Reset"
        nav_view.menu.getItem(0).isChecked=true

    }

    private fun buildGoogleApiClient() {

        if (googleApiClient != null) {
            return
        }
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build()
    }

    override fun onConnected(p0: Bundle?) {
         subscribe()
         nav_view.getHeaderView(0).hosting_switch.setOnCheckedChangeListener({ compoundButton, isChecked ->
            if(isChecked) {
                val hostedName = userData?.name!!
                roomHelper.saveHost(hostedName)
                Toast.makeText(this,"You are now discoverable to nearby people", Toast.LENGTH_SHORT).show()
            }
            else {
                val hostedName = userData?.name!!
                roomHelper.removeHost(hostedName)
                Toast.makeText(this,"You are now hidden from nearby people ", Toast.LENGTH_SHORT).show()
            }
        })

        Log.i("HomeActivity", "GoogleApiClient connected")
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    private fun subscribe() {
        Nearby.getMessagesClient(this).subscribe(mMessageListener)
    }

    private fun unsubscribe() {
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        checkDiscoverable()
    }

    fun checkDiscoverable(){
        if(nav_view.hosting_switch.isChecked) {
            val hostedName = userData?.name!!
            roomHelper.removeHost(hostedName)
        }
    }
}
