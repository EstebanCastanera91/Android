package com.dev.gestionedificios.miCuenta

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        val firstNameView: TextView = binding.userFirstName
        val lastNameView: TextView= binding.userLastname
        val emailUserView: TextView= binding.userMail
        val phoneUserView: TextView= binding.userPhone

        val ref = db.collection("user").document("pepe@gmail.com")
        ref.get().addOnSuccessListener {



            phoneUserView.text =it.get("phone") as String
            firstNameView.text=it.get("firstName") as String
            lastNameView.text=it.get("lastName") as String
            //emailUserView.text=it.get("email") as String
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}