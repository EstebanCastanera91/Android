package com.dev.gestionedificios

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.RuntimeException

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup

        val bundle:Bundle?= intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")
        setup(email?:"",provider?:"")

        //Guardado de datos

        val prefs:SharedPreferences.Editor =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }

    private fun setup(email:String, provider:String)
    {
        title ="Inicio"

        emailTextView.text =email
        providerTextView.text=provider

        logOutButton.setOnClickListener {

            //Borrado de datos al cerrar sesion

            val prefs:SharedPreferences.Editor =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()  //Volver a la pantalla anterior
        }

        //Boton para probar Falla Crashlytics

        errorButton.setOnClickListener{

            //Enviar informacion adicional
            FirebaseCrashlytics.getInstance().setUserId(email)
            FirebaseCrashlytics.getInstance().setCustomKey("provider",provider)

            //Enviar log de contexto
            FirebaseCrashlytics.getInstance().log("Se pulso el boton Forzar Error")

            //Forzado de error
            throw RuntimeException("Forzado de error")
        }


    }
}