package com.example.help

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MenuActivity : AppCompatActivity() {


    lateinit var instruction: Button
    lateinit var registerNumber: Button
    lateinit var viewNumber: Button
    lateinit var editMessage: Button


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
            startActivity(Intent(this@MenuActivity, RegisterNumber::class.java))
        }

        viewNumber.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ViewNumber::class.java))
        }

        editMessage.setOnClickListener {
            startActivity(Intent(this@MenuActivity, EditMessage::class.java))
        }
    }
}