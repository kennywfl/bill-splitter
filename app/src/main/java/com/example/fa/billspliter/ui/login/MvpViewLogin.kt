package com.example.fa.billspliter.ui.login

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface MvpViewLogin{
    fun checkSignIn()
    fun facebookSignIn()
    fun googleSignIn()
    fun handleFacebookAccessToken(token: AccessToken)
    fun handleGoogleSignIn(acct: GoogleSignInAccount)
}