package com.dev.gestionedificios

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.signUpButton
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_up : AppCompatActivity() {

    private  val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


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

        //Logica del boton Registrar
        signUpButton2.setOnClickListener {

            if (email_EditText.text.isNotEmpty() && password_EditText.text.isNotEmpty() && user_first_name.text.isNotEmpty() && user_lastname.text.isNotEmpty()&& user_phone.text.isNotEmpty())
            {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email_EditText.text.toString(),
                        password_EditText.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            /*
                            db.collection("user").document("nicolas@gmail.com").set(
                                hashMapOf(
                                    "email" to email_EditText.text.toString(),
                                    "address" to addressTextView.text.toString(),
                                    "phone" to phoneTextView.text.toString(),
                                    "firstName" to user_first_name.text.toString(),
                                    "lastName" to user_lastname.text.toString(),
                                    "provider" to ProviderType.BASIC,
                                )
                                )

                             */




                            showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            showAlert("Se ha producido un error al Registrar al usuario")
                        }
                    }
            }
            else{
                showAlert("Favor de completar todos los campos")
            }

        }
    }


    private fun showHome(email:String, provider: ProviderType)
    {

        val homeIntent= Intent(this,MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }

        startActivity(homeIntent)
    }

    //Mensaje de alerta si no se puede loguiar
    private fun showAlert(mensaje:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
