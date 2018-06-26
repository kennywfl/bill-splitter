package com.example.fa.billspliter.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    fun getDate(): String {
        val dt = SimpleDateFormat("d-MM-yyyy")
        val date = dt.format(Calendar.getInstance().time)
        return date
    }
}