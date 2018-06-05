package com.example.fa.billspliter.data.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.fa.billspliter.data.local.NearbyDao


@Database(entities = arrayOf(NearbyPeopleEntity::class),version = 1)
abstract class NearbyDatabase: RoomDatabase() {
    abstract fun NearbyDao(): NearbyDao
}