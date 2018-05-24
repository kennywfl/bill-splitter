package com.example.fa.billspliter.ui.billspliter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fa.billspliter.R
import com.example.fa.billspliter.ui.adapter.TaxRateBarAdapter
import kotlinx.android.synthetic.main.fragment_home_page.view.*


class HomeFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        view.checktax.setOnClickListener(
                {
                    if(view.checktax.isChecked) {
                        view.TaxRateLayout.visibility = View.VISIBLE
                    }
                    else {
                        view.TaxRateLayout.visibility = View.GONE
                    }

                })
        val taxRateBarAdapter: TaxRateBarAdapter = TaxRateBarAdapter(view.taxratetext,view.textamounttext,view.AmountText)
        view.taxratebar.setOnSeekBarChangeListener(taxRateBarAdapter)
        view.plusButton.setOnClickListener({
                    var data = view.PeopleText.text.toString()
                    var calculateddata = data.toInt()+1
                    view.PeopleText.setText(calculateddata.toString())
                })
             view.minusButton.setOnClickListener({
                    var data = view.PeopleText.text.toString()
                    if(data.toInt()<=0) {
                        view.PeopleText.setText("0")
                    }
                    else {
                        var calculateddata = data.toInt()-1
                        view.PeopleText.setText(calculateddata.toString())
                    }
                })

        return view
    }





}
