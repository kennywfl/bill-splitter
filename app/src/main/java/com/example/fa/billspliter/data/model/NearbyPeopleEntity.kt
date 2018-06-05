package com.example.fa.billspliter.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Nearby")
data class NearbyPeopleEntity (
        @PrimaryKey(autoGenerate = true)
        var id : Int? = null,

        @ColumnInfo(name = "name")
        var name : String = ""

)
