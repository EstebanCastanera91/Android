package com.dev.gestionedificios.miCuenta

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.dev.gestionedificios.R
import com.dev.gestionedificios.databinding.FragmentMiCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_mi_cuenta.*

class MiCuentaFragment : Fragment() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private  val db = FirebaseFirestore.getInstance()

    private var _binding: FragmentMiCuentaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val miCuentaViewModel = ViewModelProvider(this).get(MiCuentaViewModel::class.java)

        _binding= FragmentMiCuentaBinding.inflate(inflater,container,false)
        val root :View= binding.root

        val firstNameView: TextView = root.findViewById(R.id.user_first_name_miCuenta)
        val lastNameView: TextView= root.findViewById(R.id.user_lastname_miCuenta)
        val emailUserView: TextView= root.findViewById(R.id.user_mail_miCuenta)
        val phoneUserView: TextView= root.findViewById(R.id.user_phone_miCuenta)


        val button_save:Button=root.findViewById(R.id.save_profile_miCuenta)

        button_save.setOnClickListener(){
            val user_auth:String=mAuth.currentUser!!.uid

            db.collection("users").document(user_auth).set(
                hashMapOf(
                    "email" to user_mail_miCuenta.text.toString(),
                    "firstName" to user_first_name_miCuenta.text.toString(),
                    "lastName" to user_lastname_miCuenta.text.toString(),
                    "phone" to user_phone_miCuenta.text.toString(),
                    "profilePictureURL" to ""
                )
            )


            //Log.d("Click",user_auth)
        }

        val ref = db.collection("users").document(mAuth.currentUser!!.uid)
        ref.get().addOnSuccessListener {

            if (phoneUserView != null) {
                phoneUserView.text = it.get("phone") as String
            }
            else{phoneUserView.text=""}

            if (firstNameView != null) {
                firstNameView.text = it.get("firstName") as String
            }
            else{firstNameView.text=""}

            if (lastNameView != null) {
                lastNameView.text = it.get("lastName") as String
            }
            else{lastNameView.text=""}

            if (emailUserView != null) {
                emailUserView.text = it.get("email") as String
            }
            else{emailUserView.text=""}


        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}