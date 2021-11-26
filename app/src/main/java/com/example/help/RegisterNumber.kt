package com.example.help

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.help.Database.ContactDatabase
import com.example.help.Database.ContactEntity

class RegisterNumber : AppCompatActivity() {
lateinit var etName:EditText
lateinit var etNumber:EditText
lateinit var registerBtn :Button
var name:String = "Def"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_number)
        etName = findViewById(R.id.etName)
        etNumber = findViewById(R.id.etNumber)
        registerBtn = findViewById(R.id.addButton)
        val contactEntity = ContactEntity(
            3,
            etName.text.toString(),
            etNumber.text.toString()
        )
        registerBtn.setOnClickListener {
            if(!DBAsyncTask(this,contactEntity,1).execute().get()){
                val async = DBAsyncTask(this,contactEntity,2).execute()
                Log.e("this",contactEntity.toString())
            }
            Toast.makeText(this,etName.text.toString(),Toast.LENGTH_LONG).show()
        }
    }
    class DBAsyncTask(val context:Context,val contactsEntity: ContactEntity,val mode:Int):AsyncTask<Void,Void,Boolean>(){
        val db = Room.databaseBuilder(context,ContactDatabase::class.java,"Contacts").build()
        override fun doInBackground(vararg params: Void?): Boolean {
          when(mode){
              1->{
                  val contact: ContactEntity =db.contactDao().getContactById(contactsEntity.person_id.toString())
                  db.close()
                  return contact!=null
              }
              2->{
                  db.contactDao().insertContact(contactsEntity)
                  db.close()
                  return true
              }
              3->{
                    db.contactDao().deleteContact(contactsEntity)
                  db.close()
                  return true
              }

          }
            return false
        }

    }
}