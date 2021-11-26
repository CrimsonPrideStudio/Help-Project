package com.example.help.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Contacts {
@Insert
fun insertContact(contactEntity: ContactEntity)

@Delete
fun deleteContact(contactEntity: ContactEntity)

@Query("SELECT * FROM Contacts")
fun getAllContacts():List<ContactEntity>

@Query("SELECT * FROM Contacts WHERE person_id=:PersonID")
fun getContactById(PersonID:String):ContactEntity
}