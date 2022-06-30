package com.dev.gestionedificios

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.*
import com.dev.gestionedificios.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

enum class ProviderType{
    BASIC,
    GOOGLE
}


class MainActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)



        appBarConfiguration = AppBarConfiguration(
         setOf(
              R.id.nav_home,R.id.nav_misUnidades,R.id.nav_facturas,R.id.nav_myAccount
         ), drawerLayout
        )
        //Email y Proveedor
        val bundle:Bundle?= intent.extras
        val email=bundle?.getString("email")




        val header_nav_view: View = navView.getHeaderView(0)
        val email_navView: TextView= header_nav_view.findViewById(R.id.user_mail)
        val user_navView: TextView= header_nav_view.findViewById(R.id.user_name)
        val ref = db.collection("users").document(mAuth.currentUser!!.uid)
        ref.get().addOnSuccessListener {
            user_navView.text = it.get("firstName") as String
            user_navView.append(" ")
            user_navView.append(it.get("lastName") as String)
            }

        email_navView.setText(email)





        setupActionBarWithNavController(navController, appBarConfiguration)


        navView.setupWithNavController(navController)


logOut_button1.setOnClickListener {

    //Borrado de datos al cerrar sesion

     val user=mAuth.currentUser

        val prefs: SharedPreferences.Editor =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        mAuth.signOut()
        val homeIntent= Intent(this,AuthActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        startActivity(homeIntent)





}


}















override fun onCreateOptionsMenu(menu: Menu): Boolean {
// Inflate the menu; this adds items to the action bar if it is present.
menuInflater.inflate(R.menu.main, menu)
return true
}

override fun onSupportNavigateUp(): Boolean {
val navController = findNavController(R.id.nav_host_fragment_content_main)
return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
}