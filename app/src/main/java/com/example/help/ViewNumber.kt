package com.example.help

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.help.Database.ContactDatabase
import com.example.help.Database.ContactEntity
import com.example.help.adaptar.NumberRecyclerAdaptar

class ViewNumber : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter:NumberRecyclerAdaptar
    var contactDbList= listOf<ContactEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_number)
recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        contactDbList = DBAsyncTask(this).execute().get()
        Log.e("This",contactDbList.toString())
        recyclerAdapter = NumberRecyclerAdaptar(this,contactDbList)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager
    }
    class DBAsyncTask(val context: Context):
        AsyncTask<Void, Void, List<ContactEntity>>(){

        override fun doInBackground(vararg params: Void?): List<ContactEntity> {
            val dbContact = Room.databaseBuilder(context, ContactDatabase::class.java,"Contacts").build()
            return dbContact.contactDao().getAllContacts()
        }
    }
}