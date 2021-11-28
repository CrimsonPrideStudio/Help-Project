package com.example.help

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.core.content.ContextCompat.startActivity

class Reciever(context: MainActivity): BroadcastReceiver() {
    val activity:MainActivity = context
    var i=0
    override fun onReceive(p0: Context?, p1: Intent?) {

        i++
        if(i==4){
            activity.checkPermission()
        }

        val timer =object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                i=0
            Log.e("thos","skkss")
            }

        }
        timer.start()
    }
}