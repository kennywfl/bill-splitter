package com.example.fa.billspliter.ui.billspliter

import android.animation.ValueAnimator
import android.arch.persistence.room.Room
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.HistoryDatabase
import com.example.fa.billspliter.data.model.ReceivedDatabase
import com.example.fa.billspliter.ui.login.Main
import com.example.fa.billspliter.data.model.UserData
import com.example.fa.billspliter.presenter.NearbyConnectionManager
import com.example.fa.billspliter.util.DialogFactory
import com.example.fa.billspliter.util.ProgressDialogUtil
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import com.google.android.gms.nearby.connection.Strategy

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private var userData: UserData? = null
    private var mGoogleSignInClient: GoogleApiClient? = null
    private var dialogFactory = DialogFactory()
    private lateinit var preferenceHelper: PreferencesHelper
    private var googleApiClient: GoogleApiClient? = null
    private var nearbyConnectionManager = NearbyConnectionManager()
    private var Service_ID: String = "com.example.fa.billspliter.ui.billspliter"
    private var NearbyStrategy: Strategy = Strategy.P2P_CLUSTER


    companion object {
        var db: HistoryDatabase? = null
        var rdb: ReceivedDatabase? = null
        var connectionClients: ConnectionsClient? = null
        var loginType: String? = null
        var toggle: ActionBarDrawerToggle? = null
        var isArrow: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        nav_view.setNavigationItemSelectedListener(this)

        db = Room.databaseBuilder(applicationContext, HistoryDatabase::class.java, "bill").allowMainThreadQueries().build()
        rdb = Room.databaseBuilder(applicationContext, ReceivedDatabase::class.java, "rbill").allowMainThreadQueries().build()

        buildGoogleApiClient()
        onCreateDrawerToggle()

        connectionClients = Nearby.getConnectionsClient(this)
        ProgressDialogUtil(this)
    }

    private fun onCreateDrawerToggle() {
        toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle!!)
        toggle!!.syncState()

        toolbar.setNavigationOnClickListener {
            if (isArrow) {
                backToPreviousFragment()
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        initView()
    }

    private fun backToPreviousFragment() {
        onSupportNavigateUp()
        if (checkToggleState()) {
            nav_view.menu.getItem(0).isChecked = true
            changeToolbarIconToMenu()
        }
    }

    private fun checkToggleState(): Boolean {
        val current = findNavController(R.id.nav_home_fragment).currentDestination.id
        if (current == R.id.homePage) {
            return true
        }
        return false
    }

    private fun initView() {
        preferenceHelper = PreferencesHelper(this)
        userData = UserData(preferenceHelper.getName(), preferenceHelper.getEmail(), preferenceHelper.getUrl())
        loginType = preferenceHelper.getType()
        if (loginType == "google") {
            mGoogleSignInClient = GoogleApiClient.getAllClients().first()
        }
        nav_view.getHeaderView(0).tv_name.text = userData?.name
        nav_view.getHeaderView(0).tv_email.text = userData?.email

        if (userData?.url != "" && userData?.url != null) {
            Picasso.with(applicationContext).load(userData?.url).fit().into(nav_view.getHeaderView(0).imageView)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_home_fragment).navigateUp()

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (checkToggleState()) {
            dialogFactory.createTwoButtonDialog(this, "ALERT!", "Are you sure want to quit the application?",
                    DialogInterface.OnClickListener { dialog, which -> finish() }).show()
        } else {
            backToPreviousFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.support_menu, menu)
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
            R.id.home -> {
            }
            R.id.history -> {
                changeToolbarIconToBackArrow()
                Navigation.findNavController(this, R.id.nav_home_fragment).navigate(R.id.action_homePage_to_history)
            }
            R.id.nearby -> {
                changeToolbarIconToBackArrow()
                Navigation.findNavController(this, R.id.nav_home_fragment).navigate(R.id.action_homePage_to_nearby2)
            }
            R.id.sign_out -> {
                signOut()
            }
            R.id.Exit -> {
                dialogFactory.createTwoButtonDialog(this, "ALERT!", "Are you sure want to quit the application?",
                        DialogInterface.OnClickListener { dialog, which -> finish() }).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun recreate() {
        Navigation.findNavController(this, R.id.nav_home_fragment).navigate(R.id.action_homePage_self)
        nav_view.menu.getItem(0).isChecked = true
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
        nav_view.getHeaderView(0).hosting_switch.setOnCheckedChangeListener({ compoundButton, isChecked ->
            if (isChecked) {
                nearbyConnectionManager.startAdvertising(userData!!, Service_ID, NearbyStrategy)
                Toast.makeText(this, "You are now discoverable to nearby people", Toast.LENGTH_SHORT).show()
            } else {
                connectionClients?.stopAdvertising()
                Toast.makeText(this, "You are now hidden from nearby people ", Toast.LENGTH_SHORT).show()
            }
        })
        Log.i("HomeActivity", "GoogleApiClient connected")
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun checkDiscoverable() {
        if (nav_view.hosting_switch.isChecked) {
            connectionClients?.stopAdvertising()
            connectionClients?.stopDiscovery()

        }
    }

    private fun signOut() {
        preferenceHelper.clear()
        if (loginType == "google") {
            FirebaseAuth.getInstance().signOut()
            Auth.GoogleSignInApi.signOut(mGoogleSignInClient)
        } else if (loginType == "facebook") {
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut();
        }
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }

    fun changeToolbarIconToBackArrow() {
        isArrow = true
        animateIcon(0, 1, 800);
    }

    fun changeToolbarIconToMenu() {
        isArrow = false
        animateIcon(1, 0, 800);
    }

    fun animateIcon(start: Int, end: Int, duration: Int) {
        if (toggle != null) {
            val anim = ValueAnimator.ofFloat(start.toFloat(), end.toFloat());
            anim.addUpdateListener { animation ->
                val slideOffset = animation?.animatedValue as Float;
                toggle!!.onDrawerSlide(drawer_layout, slideOffset);
            };
            anim.interpolator = DecelerateInterpolator();
            anim.duration = duration.toLong();
            anim.start();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        checkDiscoverable()
    }

    override fun onResume() {
        super.onResume()
        nav_view.menu.getItem(0).isChecked = true
    }
}
