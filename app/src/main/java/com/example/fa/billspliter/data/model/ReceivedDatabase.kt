package com.example.fa.billspliter.data.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.fa.billspliter.data.local.RBillDao


@Database(entities = arrayOf(ReceivedBillEntity::class),version = 1)
abstract class ReceivedDatabase: RoomDatabase() {
    abstract fun RBillDao(): RBillDao
}