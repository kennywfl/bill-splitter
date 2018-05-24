







package com.example.fa.billspliter.ui.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.PreferencesHelper
import kotlinx.android.synthetic.main.fragment_skip_login.view.*

class SkipLogin : Fragment() {
    private lateinit var preferenceHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper= PreferencesHelper(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_skip_login, container, false)

        view.next_btn.setOnClickListener{
            if(TextUtils.isEmpty(view.inputText.text.toString())) {
                Toast.makeText(context,"Please input a username to proceed.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            preferenceHelper.saveData(view.inputText.text.toString(),"",null,"skip")
            findNavController().navigate(R.id.action_skipLogin_to_homeActivity)
            activity!!.finish();
        }
        return view
    }





}
