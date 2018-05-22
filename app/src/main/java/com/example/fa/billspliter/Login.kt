package com.example.fa.billspliter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*


class Login : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_login, container, false)

        view.button.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_login_to_homePage)
        }
        view.button2.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_login_to_homePage)
        }
        view.button3.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_login_to_homePage)
        }
        return view
    }


}
