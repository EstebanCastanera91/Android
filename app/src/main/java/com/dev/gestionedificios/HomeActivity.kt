package com.dev.gestionedificios

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.RuntimeException

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {

    private  val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup

        val bundle:Bundle?= intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")
        setup(email?:"",provider?:"")

        //<editor-fold desc="Guardado de datos de login">

        val prefs:SharedPreferences.Editor =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
        //</editor-fold>
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

        //<editor-fold desc="Boton para producir Falla">
        //Boton para probar Falla Crashlytics

        /*
        errorButton.setOnClickListener{

            //Enviar informacion adicional
            FirebaseCrashlytics.getInstance().setUserId(email)
            FirebaseCrashlytics.getInstance().setCustomKey("provider",provider)

            //Enviar log de contexto
            FirebaseCrashlytics.getInstance().log("Se pulso el boton Forzar Error")

            //Forzado de error
            throw RuntimeException("Forzado de error")
        }
        */
        //</editor-fold>

        //<editor-fold desc="Boton Guardado">

        saveButton.setOnClickListener {
            db.collection("user").document(email).set(
                hashMapOf("provider" to provider,
                    "address" to addressTextView.text.toString(),
                    "phone" to phoneTextView.text.toString()
                )
            )
        }
        //</editor-fold>

        //<editor-fold desc="Boton Recuperar">

        getButton.setOnClickListener {
            db.collection("user").document(email).get().addOnSuccessListener {
                addressTextView.setText(it.get("address") as String?)
                phoneTextView.setText(it.get("phone") as String?)
            }
        }
        //</editor-fold>

        //<editor-fold desc="Boton Eliminar">

        deleteButton.setOnClickListener {
            db.collection("user").document(email).delete()
        }
        //</editor-fold>

    }
}