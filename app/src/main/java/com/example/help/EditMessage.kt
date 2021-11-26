package com.example.help

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditMessage : AppCompatActivity() {
    lateinit var BtnClick:Button
    lateinit var etMsg:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_sos_message)
        etMsg = findViewById(R.id.etMsg)
        BtnClick = findViewById(R.id.Done)
        BtnClick.setOnClickListener {
            Toast.makeText(this,etMsg.text.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}