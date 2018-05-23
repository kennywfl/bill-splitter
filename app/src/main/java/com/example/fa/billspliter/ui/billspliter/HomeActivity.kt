package com.example.fa.billspliter.ui.billspliter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import com.example.fa.billspliter.data.PreferencesHelper
import com.example.fa.billspliter.R
import com.example.fa.billspliter.ui.login.Main
import com.example.fa.billspliter.data.model.UserData
import com.example.fa.billspliter.util.DialogFactory
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private  var userData : UserData?= null
    private var mGoogleSignInClient : GoogleApiClient?= null
    private var dialogFactory = DialogFactory()
    private lateinit var preferenceHelper: PreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        preferenceHelper= PreferencesHelper(this)

        userData= UserData(preferenceHelper.getName(), preferenceHelper.getEmail(), preferenceHelper.getUrl())
        val type =preferenceHelper.getType()


        if(type == "google") {
            mGoogleSignInClient = GoogleApiClient.getAllClients().first()
            nav_view.getHeaderView(0).tv_name.text=userData?.name
            nav_view.getHeaderView(0).tv_email.text=userData?.email
        }


    }
    override fun onSupportNavigateUp()
            = findNavController(R.id.my_nav_host_fragment).navigateUp()

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
                return true
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
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

    fun signOut()
    {
        preferenceHelper.clear()
        FirebaseAuth.getInstance().signOut()
        Auth.GoogleSignInApi.signOut(mGoogleSignInClient)
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }

}
