package com.example.help

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.example.help.Database.ContactDatabase
import com.example.help.Database.ContactEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var menu: Button
    lateinit var sos: Button
    private lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationClient: FusedLocationProviderClient
    var contactDbList= listOf<ContactEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        menu = findViewById(R.id.menu)
        sos = findViewById(R.id.help)


        val reciever:Reciever = Reciever(this)
        registerReceiver(reciever, IntentFilter(Intent.ACTION_SCREEN_ON))
        registerReceiver(reciever, IntentFilter(Intent.ACTION_SCREEN_OFF))
        menu.setOnClickListener {
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
        }

        sos.setOnClickListener {

            checkPermission()

           // call()

        }

    }
    private fun sendSms( longitude:String,latitude:String){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "button is clicked", Toast.LENGTH_SHORT).show()

            val smsManager: SmsManager = SmsManager.getDefault()
            for (i in contactDbList) {
                smsManager.sendTextMessage(
                    i.personNumber.toString(),
                    null,
                    "i need help https://maps.google.com/?q=${longitude},${latitude}",
                    null,
                    null
                )
            }

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 101)

            }
        }
    }
    private fun call(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "call button clicked", Toast.LENGTH_SHORT).show()
            var intent:Intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:"+"+91"+contactDbList[0].personNumber))
            startActivity(intent)
            // startActivity(Intent(Intent.ACTION_CALL, Uri.parse(numbers[1].toString())))

        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),100)
            }
        }
    }

    fun checkPermission(){
        if ((ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
                    )
        ) {
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS),100)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getCurrentLocation() {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    val location: Location = it.result
                    contactDbList = DBAsyncTask(this).execute().get()

                    if (location != null) {
                        sendSms(location.longitude.toString(),location.latitude.toString())
                        call()
                        //Toast.makeText(this@MainActivity, contactDbList[0].personNumber.toString()+""+location.latitude.toString() + "  " + location.longitude.toString() , Toast.LENGTH_SHORT).show()
                    } else {
                        val locationRequest: com.google.android.gms.location.LocationRequest =
                            com.google.android.gms.location.LocationRequest.create()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            locationRequest.priority = LocationRequest.QUALITY_HIGH_ACCURACY
                        }
                        locationRequest.interval = 10000
                        locationRequest.fastestInterval = 1000
                        locationRequest.numUpdates = 1
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest, locationCallback,
                            Looper.myLooper()
                        )
                    }

                }
            }else{

                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }

        }
    }
    class DBAsyncTask(val context: Context):
        AsyncTask<Void, Void, List<ContactEntity>>(){

        override fun doInBackground(vararg params: Void?): List<ContactEntity> {
            val dbContact = Room.databaseBuilder(context, ContactDatabase::class.java,"Contacts").build()
            return dbContact.contactDao().getAllContacts()
        }
    }

}
