package com.dev.gestionedificios

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class Login : AppCompatActivity() {

    //private val GOOGLE_SIGN_IN=100

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



    private fun setup(){

        //Logica del boton acceder
        loginButton.setOnClickListener {

             if (emailEditText.text.isNotEmpty()&& passwordEditText.text.isNotEmpty())
             {
                 FirebaseAuth.getInstance()
                     .signInWithEmailAndPassword(emailEditText.text.toString(),
                         passwordEditText.text.toString()).addOnCompleteListener {
                         if(it.isSuccessful)
                         {
                             showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                         }else{
                             showAlert()
                         }
                     }
             }
        }


        /*
            val googleConf: GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient =GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
        }

         */

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

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==GOOGLE_SIGN_IN)
        {
            val task: Task<GoogleSignInAccount> =GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account= task.getResult(ApiException::class.java)

                if(account !=null){
                    val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)

                    if(task.isSuccessful )
                    {
                        showHome(account.email?:"",ProviderType.GOOGLE)
                    }else{
                        showAlert()
                    }
                }
            }catch (e:ApiException){
                showAlert()
            }

        }
    }

     */


}