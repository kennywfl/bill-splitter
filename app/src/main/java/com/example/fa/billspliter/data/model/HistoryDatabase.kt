package com.example.fa.billspliter.data.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.fa.billspliter.data.BillDao


@Database(entities = arrayOf(BillEntity::class),version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun billDao(): BillDao
}