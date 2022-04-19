package com.dev.gestionedificios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Eventos Personalizados de Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle =Bundle()
        bundle.putString("message","Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)
    }
}