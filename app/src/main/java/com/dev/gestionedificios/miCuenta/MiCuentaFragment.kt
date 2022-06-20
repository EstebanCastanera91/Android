package com.dev.gestionedificios.miCuenta

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.gestionedificios.R
import com.dev.gestionedificios.databinding.FragmentMiCuentaBinding

class MiCuentaFragment : Fragment() {


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

        //val textView: TextView = binding.textHome
        //miCuentaViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}