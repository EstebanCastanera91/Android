package com.dev.gestionedificios

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //


        //Eventos Personalizados de Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)


        //Setup
        setup()


    }



    //Si haces LogOut vuelve a mostrar la pantalla Auth
    override fun onStart() {
        super.onStart()
        authLayout.visibility = View.VISIBLE
    }


    private fun setup() {
        title = "Autenticacion"

        //Logica del boton Registrar
        signUpButton.setOnClickListener {
            val homeIntent = Intent(this, Sign_up::class.java)
            startActivity(homeIntent)

        }

        //Logica del boton acceder
        accederButton.setOnClickListener {

            val homeIntent = Intent(this, Login::class.java)
            startActivity(homeIntent)

        }


    }
}