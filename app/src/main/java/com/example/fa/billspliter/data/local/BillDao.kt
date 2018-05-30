package com.example.fa.billspliter.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.fa.billspliter.data.model.BillEntity


@Dao
interface BillDao {

    @Query("SELECT * FROM Bill")
    fun getBillHistory(): List<BillEntity>

    @Insert
    fun addBill(bill: BillEntity)

    @Delete
    fun deleteBill(bill: BillEntity)

    @Query("DELETE FROM Bill")
    fun deleteTable()
}