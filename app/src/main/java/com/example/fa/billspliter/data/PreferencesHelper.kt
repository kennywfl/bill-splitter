package com.example.fa.billspliter.data
import android.content.Context
import android.content.SharedPreferences


/* This class is use for processing the share preferences data. */
open class PreferencesHelper{

    val PREF_FILE_NAME = "user_data"
    private var context:Context
    private lateinit var mPref: SharedPreferences


    constructor(context: Context) {
        this.context = context
    }


    fun saveData(name:String ,email: String? , url: String?, type: String) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = mPref.edit()
        editor.putString("name",name)
        editor.putString("email", email)
        editor.putString("url", url)
        editor.putString("type", type)
        editor.apply()
    }

    /* Retrieve name. */
    fun getName():String? {
        val sharePref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharePref.getString("name", null)
    }

    /* Retrieve email. */
    fun getEmail():String? {
        val sharePref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharePref.getString("email", "")
    }
    /* Retrieve name. */
    fun getUrl():String? {
        val sharePref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharePref.getString("url", "")
    }

    /* Retrieve name. */
    fun getType():String {
        val sharePref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharePref.getString("type","")
    }
    /* Clear the share preferences*/
    fun clear() {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        mPref.edit().clear().apply()

    }


}
