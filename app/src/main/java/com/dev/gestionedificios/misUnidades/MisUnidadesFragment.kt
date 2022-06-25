package com.dev.gestionedificios.misUnidades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.gestionedificios.databinding.FragmentMisunidadesBinding


class MisUnidadesFragment : Fragment() {

    private var _binding:FragmentMisunidadesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val misUnidadesViewModel =
            ViewModelProvider(this).get(MisUnidadesViewModel::class.java)
        _binding = FragmentMisunidadesBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}