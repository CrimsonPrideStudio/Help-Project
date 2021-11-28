package com.example.help

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.help.Database.ContactDatabase
import com.example.help.Database.ContactEntity

class MenuActivity : AppCompatActivity() {


    lateinit var instruction: Button
    lateinit var registerNumber: Button
    lateinit var viewNumber: Button
    lateinit var editMessage: Button

    var contactDbList= listOf<ContactEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)


        instruction = findViewById(R.id.instruction)
       registerNumber =  findViewById(R.id.registerNumber)
        viewNumber =  findViewById(R.id.viewNumber)
        editMessage =  findViewById(R.id.editMesage)

        instruction.setOnClickListener {
            startActivity(Intent(this@MenuActivity, InstructionActivity::class.java))
        }

        registerNumber.setOnClickListener {
//            startActivity(Intent(this@MenuActivity, RegisterNumber::class.java))

            val view = LayoutInflater.from(this).inflate(R.layout.prompt,null)
            val userName:EditText = view.findViewById(R.id.etPromptName)
            val userNumber:EditText = view.findViewById(R.id.etPromptNumber)
            val dialog = AlertDialog.Builder(this )
            dialog.setView(view)
            dialog.setTitle("Failed")
            dialog.setCancelable(false).setPositiveButton("Add"){text,listener->
                registerNumber(userName.text.toString(),userNumber.text.toString())
            }
dialog.create()
            dialog.show()


        }

        viewNumber.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ViewNumber::class.java))
        }

        editMessage.setOnClickListener {
            startActivity(Intent(this@MenuActivity, EditMessage::class.java))
        }
    }
fun registerNumber(userName:String,userNumber:String){
    val contactEntity = ContactEntity(
        contactDbList.size+1,
        userName.toString(),
        userNumber.toString()
    )

    if(!MenuActivity.DBAsyncTask(this, contactEntity, 1).execute().get()){
        val async = MenuActivity.DBAsyncTask(this, contactEntity, 2).execute()
        Log.e("this",contactEntity.toString())
    }
}
    class DBAsyncTask(val context:Context, val contactsEntity: ContactEntity, val mode:Int):
        AsyncTask<Void, Void, Boolean>(){
        val db = Room.databaseBuilder(context, ContactDatabase::class.java,"Contacts").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode){
                1->{
                    val contact: ContactEntity =db.contactDao().getContactById(contactsEntity.person_id)
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
    class DBAsyncInfo(val context: Context):
        AsyncTask<Void, Void, List<ContactEntity>>(){

        override fun doInBackground(vararg params: Void?): List<ContactEntity> {
            val dbContact = Room.databaseBuilder(context, ContactDatabase::class.java,"Contacts").build()
            return dbContact.contactDao().getAllContacts()
        }
    }
}