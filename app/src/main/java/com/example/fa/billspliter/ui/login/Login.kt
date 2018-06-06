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
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.data.local.PreferencesHelper
import com.example.fa.billspliter.R
import com.google.firebase.auth.FirebaseAuth

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class Login : Fragment() {

    private var mAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1;
    private var mGoogleSignInClient : GoogleApiClient ?= null
    private lateinit var preferenceHelper: PreferencesHelper
    private lateinit var mCallbackManager : CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance();
        preferenceHelper= PreferencesHelper(context!!)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        checkSignIn()
        view.gsign_btn.setOnClickListener { view ->
            googleSignIn()
        }

        view.fsign_btn.setOnClickListener {
            facebookSignIn()
        }

        view.ssign_btn.setOnClickListener { view ->
            findNavController().navigate(R.id.action_login_to_skipLogin)
        }
        return view
    }

    fun checkSignIn() {
        preferenceHelper= PreferencesHelper(context!!)
        if(preferenceHelper.getName() != null ) {
            if(preferenceHelper.getType() == "google") {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                mGoogleSignInClient = GoogleApiClient.Builder(context!!).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
                mGoogleSignInClient?.connect()
            }
            findNavController().navigate(R.id.action_login_to_homeActivity)
            activity!!.finish();
        }
    }

    fun facebookSignIn()
    {
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Log.d("FacebookError",error.toString())
            }
        })
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleApiClient.Builder(context!!).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()



        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                handleGoogleSignIn(account)
            } catch (e: ApiException) {
            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    fun handleFacebookAccessToken(token:AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token);
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful()) {
                val acct = mAuth!!.currentUser;
                preferenceHelper.saveData(acct?.displayName!!, acct?.email, acct?.photoUrl.toString(), "facebook")
                findNavController().navigate(R.id.action_login_to_homeActivity)
                activity!!.finish();
            }
        })
    }

    fun handleGoogleSignIn(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener( OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful()) {
                mGoogleSignInClient?.connect()
                val acct = GoogleSignIn.getLastSignedInAccount(context!!)
                preferenceHelper.saveData(acct!!.displayName!!, acct?.email, acct?.photoUrl.toString(),"google")
                findNavController().navigate(R.id.action_login_to_homeActivity)
                activity!!.finish();
            }
        })
    }

}
