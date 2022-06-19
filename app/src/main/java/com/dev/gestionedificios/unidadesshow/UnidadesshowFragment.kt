package com.dev.gestionedificios.unidadesshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.gestionedificios.databinding.FragmentUnidadeshowBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class UnidadesshowFragment : Fragment() {

    private var _binding: FragmentUnidadeshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(UnidadeshowViewModel::class.java)

        _binding = FragmentUnidadeshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textUnidadesshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        logOutButton.setOnClickListener {


            FirebaseAuth.getInstance().signOut()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}