package com.dev.gestionedificios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //Eventos Personalizados de Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle =Bundle()
        bundle.putString("message","Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)

        //Setup
        setup()

    }

    private fun setup(){
        title ="Autenticacion"

        //logica del boton Registrar
        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty()&& passworEditText.text.isNotEmpty())
            {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailEditText.text.toString(),
                    passworEditText.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }

        //Logica del boton acceder

        loginButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty()&& passworEditText.text.isNotEmpty())
            {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailEditText.text.toString(),
                        passworEditText.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }
    private fun showAlert(){
         val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }

    private fun showHome(email:String, provider: ProviderType)
    {
        val homeIntent= Intent(this,HomeActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }
}