package com.example.help

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var menu: Button
    lateinit var sos: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menu = findViewById(R.id.menu)
        sos = findViewById(R.id.help)

        menu.setOnClickListener {
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
        }

        sos.setOnClickListener {
            Toast.makeText(this@MainActivity, "Clicked" , Toast.LENGTH_SHORT).show()
        }

    }
}