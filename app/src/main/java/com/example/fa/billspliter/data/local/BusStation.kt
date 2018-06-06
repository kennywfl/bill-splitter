package com.example.fa.billspliter.data.local

import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

class BusStation {
    companion object {
            var bus:Bus = Bus(ThreadEnforcer.ANY)
    }


}