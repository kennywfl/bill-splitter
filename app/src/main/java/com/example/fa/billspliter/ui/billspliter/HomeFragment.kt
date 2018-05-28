package com.example.fa.billspliter.ui.billspliter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fa.billspliter.R
import com.example.fa.billspliter.ui.adapter.BillAmountTextWatcherAdapter
import com.example.fa.billspliter.ui.adapter.DiscountRateTextWatcherAdapter
import com.example.fa.billspliter.ui.adapter.TaxRateBarAdapter
import com.example.fa.billspliter.ui.adapter.TaxRateTextWatcherAdapter
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import org.w3c.dom.Text


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
        view.checkdiscount.setOnClickListener(
                {
                    if(view.checkdiscount.isChecked) {
                        view.DiscountLayout.visibility = View.VISIBLE
                    }
                    else {
                        view.DiscountLayout.visibility = View.GONE
                    }

                })
        setBillTextAdapter(view)
        setTaxRateAdapter(view)
        setDiscountextAdapter(view)
        val taxRateBarAdapter:TaxRateBarAdapter = TaxRateBarAdapter(view.taxratetext,view.textamounttext,view.AmountText,view.TotalAmountMoneyText,view.DiscountText)
        view.taxratebar.setOnSeekBarChangeListener(taxRateBarAdapter)
        view.plusButton.setOnClickListener({
                    val data = view.PeopleText.text.toString()
                    val calculateddata = data.toInt()+1
                    view.PeopleText.setText(calculateddata.toString())
                })
        view.minusButton.setOnClickListener({
                    val data = view.PeopleText.text.toString()
                    if(data.toInt()<=0) {
                        view.PeopleText.setText("0")
                    }
                    else {
                        val calculateddata = data.toInt()-1
                        view.PeopleText.setText(calculateddata.toString())
                    }
                })

        return view
    }


    private fun setBillTextAdapter(view:View)
    {
        val textWactcher:TextWatcher = BillAmountTextWatcherAdapter(
                view.TotalAmountMoneyText,
                view.taxratetext,
                view.textamounttext,
                view.AmountText,
                view.DiscountText
        )
        view.AmountText.addTextChangedListener(textWactcher)
    }

    private fun setTaxRateAdapter(view:View)
    {
            val textWatcher:TextWatcher = TaxRateTextWatcherAdapter(
                    view.taxratebar,
                    view.textamounttext,
                    view.AmountText,
                    view.TotalAmountMoneyText,
                    view.taxratetext
            )

        view.taxratetext.addTextChangedListener(textWatcher)
    }

    private fun setDiscountextAdapter(view:View)
    {
        val textWatcher:TextWatcher = DiscountRateTextWatcherAdapter(
                view.DiscountText,
                view.TotalAmountMoneyText,
                view.AmountText,
                view.textamounttext
        )
        view.DiscountText.addTextChangedListener(textWatcher)
    }




}
