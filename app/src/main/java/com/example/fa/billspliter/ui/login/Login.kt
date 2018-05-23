package com.example.fa.billspliter.ui.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.view.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.data.PreferencesHelper
import com.example.fa.billspliter.R
import com.example.fa.billspliter.presenter.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient



class Login : Fragment() {

    private var mAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1;
    private var mGoogleSignInClient : GoogleApiClient ?= null
    private  var firebaseHelper= FirebaseHelper()
    private lateinit var preferenceHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance();
        preferenceHelper= PreferencesHelper(context!!)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleApiClient.Builder(context!!).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        checkSignIn()
        view.gsign_btn.setOnClickListener { view ->
            //view.findNavController().navigate(R.id.action_login_to_homePage)
            googleSignIn()
        }
        view.fsign_btn.setOnClickListener { view ->
           // view.findNavController().navigate(R.id.action_login_to_homePage)
        }
        view.ssign_btn.setOnClickListener { view ->
           // view.findNavController().navigate(R.id.action_login_to_homePage)
        }
        return view
    }

    fun checkSignIn() {
        preferenceHelper= PreferencesHelper(context!!)
        if(preferenceHelper.getName() != null) {
            mGoogleSignInClient?.connect()
            findNavController().navigate(R.id.action_login_to_homeActivity)
            activity!!.finish();
        }

    }

    private fun googleSignIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseHelper.firebaseAuthWithGoogle(account,mAuth)
                if(mAuth!=null) {
                    mGoogleSignInClient?.connect()
                    val acct = GoogleSignIn.getLastSignedInAccount(context!!)
                    preferenceHelper.saveData(acct!!.displayName!!, acct?.email, acct?.photoUrl.toString(),"google")
                    findNavController().navigate(R.id.action_login_to_homeActivity)
                    activity!!.finish();
                }
            } catch (e: ApiException) {

            }
        }
    }


}
