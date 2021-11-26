package com.example.help.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class ContactEntity(
    @PrimaryKey val person_id:Int,
    @ColumnInfo(name = "Person_name") val personName:String,
    @ColumnInfo(name = "Person_number") val personNumber:String
) {
}