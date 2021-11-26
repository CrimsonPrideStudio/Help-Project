package com.example.help.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDatabase:RoomDatabase() {
    abstract fun contactDao():Contacts
}