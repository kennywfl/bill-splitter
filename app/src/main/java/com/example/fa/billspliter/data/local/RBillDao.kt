package com.example.fa.billspliter.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.fa.billspliter.data.model.ReceivedBillEntity


@Dao
interface RBillDao {

    @Query("SELECT * FROM RBill")
    fun getBillHistory(): List<ReceivedBillEntity>

    @Insert
    fun addBill(bill: ReceivedBillEntity)

    @Delete
    fun deleteBill(bill: ReceivedBillEntity)

    @Query("DELETE FROM RBill")
    fun deleteTable()
}