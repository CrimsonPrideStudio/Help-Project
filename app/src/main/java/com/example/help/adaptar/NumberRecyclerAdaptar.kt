package com.example.help.adaptar

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.help.Database.ContactDatabase
import com.example.help.Database.ContactEntity
import com.example.help.R
import com.example.help.ViewNumber

class NumberRecyclerAdaptar(val context: Context,val numberList:List<ContactEntity>): RecyclerView.Adapter<NumberRecyclerAdaptar.NumberViewHolder>() {
    class NumberViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val personNameText:TextView = view.findViewById(R.id.txtname)
        val personIdText:TextView = view.findViewById(R.id.txtId)
        val personNumberText:TextView = view.findViewById(R.id.txtPhoneNumber)
        val favoriteBtn:Button = view.findViewById(R.id.favourite)
        val delete:Button = view.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_number,parent,false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val value = numberList[position]
        holder.personIdText.text = value.person_id.toString()
        holder.personNameText.text = value.personName
        holder.personNumberText.text = value.personNumber
        holder.delete.setOnClickListener {
            val contactEntity = ContactEntity(
                value.person_id,
                value.personName.toString(),
                value.personNumber.toString()
            )
            Log.e("This","Dee")
            val result = DBAsyncTask(context,contactEntity,3).execute().get()
            context.startActivity(Intent(context,ViewNumber::class.java))
        }
    }


    override fun getItemCount(): Int {
        return numberList.size
    }
    class DBAsyncTask(val context:Context,val contactsEntity: ContactEntity,val mode:Int):
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
}