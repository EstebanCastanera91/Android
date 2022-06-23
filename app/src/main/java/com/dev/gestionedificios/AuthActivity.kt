package com.dev.gestionedificios

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {

    //private val GOOGLE_SIGN_IN=100

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

    //Si haces LogOut vuelve a mostrar la pantalla Auth
    override fun onStart(){
        super.onStart()
        authLayout.visibility= View.VISIBLE
    }




    private fun setup(){
        title ="Autenticacion"

        //Logica del boton Registrar
        signUpButton.setOnClickListener {
            val homeIntent= Intent(this,Sign_up::class.java)
            startActivity(homeIntent)
            /*
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
             */

        }

        //Logica del boton acceder
        accederButton.setOnClickListener {

            val homeIntent= Intent(this,Login::class.java)
            startActivity(homeIntent)
           /*
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

            */
        }

        /*

        googleButton.setOnClickListener {
            //Configuracion autenticacion google

            val googleConf:GoogleSignInOptions =
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


/*
    //Mensaje de alerta si no se puede loguiar
    private fun showAlert(){
         val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }
*/

    //Funcion redirige a la pantalla de Home
    /*
    private fun showHome(email:String, provider: ProviderType)
    {

            val homeIntent= Intent(this,MainActivity::class.java).apply{
            putExtra("email",email)
            putExtra("provider",provider.name)
        }


        startActivity(homeIntent)
    }
    */
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