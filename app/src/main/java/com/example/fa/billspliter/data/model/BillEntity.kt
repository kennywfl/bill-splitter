package com.example.fa.billspliter.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Bill")
data class BillEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "amount")
        var amount: String = "",

        @ColumnInfo(name = "numPeople")
        var numPeople: String = "1",

        @ColumnInfo(name = "tax")
        var tax: String = "",

        @ColumnInfo(name = "discount")
        var discount: String = "",

        @ColumnInfo(name = "totalPaid")
        var totalPaid: String = "",

        @ColumnInfo(name = "eachPaid")
        var eachPaid: String = "",

        @ColumnInfo(name = "date")
        var date: String = "",

        @ColumnInfo(name = "serverKey")
        var serverKey: String? = null

) : Serializable
