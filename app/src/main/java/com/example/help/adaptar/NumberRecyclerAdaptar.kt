package com.example.help.adaptar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.help.Database.ContactEntity
import com.example.help.R

class NumberRecyclerAdaptar(val context: Context,val numberList:List<ContactEntity>): RecyclerView.Adapter<NumberRecyclerAdaptar.NumberViewHolder>() {
    class NumberViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val personNameText:TextView = view.findViewById(R.id.txtname)
        val personIdText:TextView = view.findViewById(R.id.txtId)
        val personNumberText:TextView = view.findViewById(R.id.txtPhoneNumber)
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
    }

    override fun getItemCount(): Int {
        return numberList.size
    }
}