package com.example.fa.billspliter.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.data.model.NearbyPeopleEntity


@Dao
interface NearbyDao {

    @Query("SELECT * FROM Nearby")
    fun getNearbyHistory(): List<NearbyPeopleEntity>

    @Insert
    fun addNearby(nearbyPeople: NearbyPeopleEntity)

    @Delete
    fun deleteBill(nearbyPeople: NearbyPeopleEntity)

    @Query("DELETE FROM nearby")
    fun deleteTable()
}