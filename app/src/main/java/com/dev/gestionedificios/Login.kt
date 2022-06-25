package com.dev.gestionedificios

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val current_user= mAuth.currentUser
        if(current_user!=null)
        {
            Log.d("user",current_user.email.toString())

        }
        //Setup
        setup()
        session()
    }




    //Comprobar si hay una sesion activa
    private fun session(){
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email:String? =prefs.getString("email",null)
        val provider:String? = prefs.getString("provider",null)

        if(email !=null && provider != null) //verificar si ya hay una session activa
        {
            authLayout.visibility= View.INVISIBLE  //Si esta logiado no muestra el authLayout
            showHome(email, ProviderType.valueOf(provider)) //navega a Home
        }

    }



    private fun setup() {

        //Logica del boton acceder
        loginButton.setOnClickListener {

            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }



    }






    //Mensaje de alerta si no se puede loguiar
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }


    //Funcion redirige a la pantalla de MAIN
    private fun showHome(email:String, provider: ProviderType)
    {

        val homeIntent= Intent(this,MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }


        startActivity(homeIntent)
    }







}
